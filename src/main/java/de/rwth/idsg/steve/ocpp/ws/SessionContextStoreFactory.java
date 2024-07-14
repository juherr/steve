/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2023 SteVe Community Team
 * All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.rwth.idsg.steve.ocpp.ws;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionContextStoreFactory {

    @Bean
    @Qualifier("sessionContextStore12")
    public SessionContextStore sessionContextStore12() {
        return new SessionContextStore();
    }

    @Bean
    @Qualifier("sessionContextStore15")
    public SessionContextStore sessionContextStore15() {
        return new SessionContextStore();
    }

    @Bean
    @Qualifier("sessionContextStore16")
    public SessionContextStore sessionContextStore16() {
        return new SessionContextStore();
    }
}
