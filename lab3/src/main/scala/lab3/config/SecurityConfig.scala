package lab3.config

import lab3.config.security.JwtConfig

final case class SecurityConfig(
    clients: ???,
    jwt: JwtConfig
)
