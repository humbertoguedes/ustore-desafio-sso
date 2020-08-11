# ustore-desafio-sso

Implementação de um SSO.

Bibliotecas:
1. Spring Boot
2. Spring Web
3. Spring Security
4. JTW -> jtw.io para manipulação do token
5. Spring Feign

Não foi utilizado a implementação do Spring SSO.

SSO -> APP Single Sign On
SSo Commons -> Tratamento do token pelo JWTRequestFilter e Autenticação por usuário e senha.
APP-Shop -> Carrinho de Compras
APP-Pay -> Pagamentos
APP UI -> È a aplicaço principal que consome os serviços APP-Shop e APP-Pay

Para as chamadas das apis de APP-Shop e APP-Pay foi utilizado a Feign para abstração das requisições.
