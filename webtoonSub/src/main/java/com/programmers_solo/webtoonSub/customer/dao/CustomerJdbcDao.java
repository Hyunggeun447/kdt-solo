package com.programmers_solo.webtoonSub.customer.dao;

import com.programmers_solo.webtoonSub.customer.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toLocalDateTime;
import static com.programmers_solo.webtoonSub.utils.JdbcUtils.toUUID;

@Repository
@RequiredArgsConstructor
public class CustomerJdbcDao implements CustomerDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL = "SELECT * FROM customers";
    private final String SELECT_BY_CUSTOMER_ID_SQL = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_CUSTOMER_EMAIL_SQL = "SELECT * FROM customers WHERE customer_email = :customerEmail";
    private final String INSERT_CUSTOMER_SQL = "INSERT INTO customers(customer_id, customer_email, password, wallet, expiry_subscription_date, created_at, updated_at, last_login_at)" +
            " VALUES(UUID_TO_BIN(:customerId), :customerEmail, :password, :wallet, :expirySubscriptionDate, :createdAt, :updatedAt, :lastLoginAt)";
    private final String UPDATE_CUSTOMER_SQL = "UPDATE customers SET password = :password, wallet = :wallet, expiry_subscription_date = :expirySubscriptionDate, updated_at = :updatedAt, last_login_at = :lastLoginAt WHERE customer_id = UUID_TO_BIN(:customerId)";
    private final String DELETE_ALL_CUSTOMERS_SQL = "DELETE FROM customers";



    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_CUSTOMER_ID_SQL,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()), customerRowMapper));
        } catch (EmptyResultDataAccessException | NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SELECT_BY_CUSTOMER_EMAIL_SQL,
                    Collections.singletonMap("customerEmail", email), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer insert(Customer customer) {
        int insert = jdbcTemplate.update(INSERT_CUSTOMER_SQL, toCustomerParamMap(customer));
        if (insert != 1) {
            throw new RuntimeException("Not insert");
        }
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update(UPDATE_CUSTOMER_SQL, toCustomerParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Not update");
        }
        return customer;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_CUSTOMERS_SQL, Collections.emptyMap());
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {

        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerEmail = resultSet.getString("customer_email");
        String password = resultSet.getString("password");
        long wallet = Long.valueOf(resultSet.getString("wallet"));
        LocalDateTime expirySubscriptionDate = toLocalDateTime(resultSet.getTimestamp("expiry_subscription_date"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        LocalDateTime lastLoginAt = toLocalDateTime(resultSet.getTimestamp("last_login_at"));

        return Customer.builder()
                .customerId(customerId)
                .customerEmail(customerEmail)
                .password(password)
                .wallet(wallet)
                .expirySubscriptionDate(expirySubscriptionDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .lastLoginAt(lastLoginAt)
                .build();
    };

    private Map<String, Object> toCustomerParamMap(Customer customer) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("customerEmail", customer.getCustomerEmail());
        paramMap.put("password", customer.getPassword());
        paramMap.put("wallet", customer.getWallet());
        paramMap.put("expirySubscriptionDate", customer.getExpirySubscriptionDate());
        paramMap.put("createdAt", customer.getCreatedAt());
        paramMap.put("updatedAt", customer.getUpdatedAt());
        paramMap.put("lastLoginAt", customer.getLastLoginAt());
        return paramMap;
    }

}
