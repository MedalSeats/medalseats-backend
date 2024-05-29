package com.medalseats.adapter.r2dbc.account.queries

import com.medalseats.adapter.r2dbc.common.DefaultSqlQueries
import com.unicamp.medalseats.account.AccountId

object AccountSqlQueries : DefaultSqlQueries() {

    fun selectAccount() =
        """
            SELECT
                id,
                name,
                email,
                birthday,
                password
            FROM account
            WHERE 1 = 1
        """

    fun insertAccount() =
        """
        INSERT INTO account
        (id, name, email, birthday, password)
        VALUES (
            :id,
            :name,
            :email,
            :birthday,
            :password
        )  
        """

    fun whereId(id: AccountId?) =
        id?.let {
            """
                AND id = :id
            """
        }

    fun whereEmail(email: String?) =
        email?.let {
            """
                AND email = :email
            """
        }

    fun wherePassword(password: String?) =
        password?.let {
            """
                AND password = :password
            """
        }
}
