##oauth2详解
---
###包
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-oauth2</artifactId>
    </dependency>
 
######启动类注解 @EnableAuthorizationServer

###Spring OAuth2.0 提供者实现原理

    Spring OAuth2.0提供者实际上分为：
    授权服务 Authorization Service.
    资源服务 Resource Service.
    虽然这两个提供者有时候可能存在同一个应用程序中，但在Spring Security OAuth中你可以把
    他它们各自放在不同的应用上，而且你可以有多个资源服务，它们共享同一个中央授权服
    务。
     
    所有获取令牌的请求都将会在Spring MVC controller endpoints中进行处理，并且访问受保护
    的资源服务的处理流程将会放在标准的Spring Security请求过滤器中(filters)。
     
    下面是配置一个授权服务必须要实现的endpoints：
    AuthorizationEndpoint：用来作为请求者获得授权的服务，默认的URL是/oauth/authorize.
    TokenEndpoint：用来作为请求者获得令牌（Token）的服务，默认的URL是/oauth/token.
     
    下面是配置一个资源服务必须要实现的过滤器：
    OAuth2AuthenticationProcessingFilter：用来作为认证令牌（Token）的一个处理流程过滤器。只有当过滤器通过之后，请求者才能获得受保护的资源。
     
    配置提供者（授权、资源）都可以通过简单的Java注解@Configuration来进行适配，你也可以使用基于XML的声明式语法来进行配置，如果你打算这样做的话，那么请使用http://www.springframework.org/schema/security/spring-security-oauth2.xsd来作为XML的schema（即XML概要定义）以及使用http://www.springframework.org/schema/security/oauth2来作为命名空间。
     
     
    一、授权服务配置：
    配置一个授权服务，你需要考虑几种授权类型（Grant Type），不同的授权类型为客户端（Client）提供了不同的获取令牌（Token）方式，为了实现并确定这几种授权，需要配置使用 ClientDetailsService 和 TokenService 来开启或者禁用这几种授权机制。到这里就请注意了，不管你使用什么样的授权类型（Grant Type），每一个客户端（Client）都能够通过明确的配置以及权限来实现不同的授权访问机制。这也就是说，假如你提供了一个支持"client_credentials"的授权方式，并不意味着客户端就需要使用这种方式来获得授权。下面是几种授权类型的列表，具体授权机制的含义可以参见RFC6749(中文版本)：
    authorization_code：授权码类型。
    implicit：隐式授权类型。
    password：资源所有者（即用户）密码类型。
    client_credentials：客户端凭据（客户端ID以及Key）类型。
    refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
     
    可以用 @EnableAuthorizationServer 注解来配置OAuth2.0 授权服务机制，通过使用@Bean注解的几个方法一起来配置这个授权服务。下面咱们介绍几个配置类，这几个配置是由Spring创建的独立的配置对象，它们会被Spring传入AuthorizationServerConfigurer中：
    ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
    AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束.
    AuthorizationServerEndpointsConfigurer：用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
    （译者注：以上的配置可以选择继承AuthorizationServerConfigurerAdapter并且覆写其中的三个configure方法来进行配置。）
     
    配置授权服务一个比较重要的方面就是提供一个授权码给一个OAuth客户端（通过 authorization_code 授权类型），一个授权码的获取是OAuth客户端跳转到一个授权页面，然后通过验证授权之后服务器重定向到OAuth客户端，并且在重定向连接中附带返回一个授权码。
     
    如果你是通过XML来进行配置的话，那么可以使用 <authorization-server/> 标签来进行配置。
     
    （译者注：想想现在国内各大平台的社会化登陆服务，例如腾讯，用户要使用QQ登录到某个网站，这个网站是跳转到了腾讯的登陆授权页面，然后用户登录并且确定授权之后跳转回目标网站，这种授权方式规范在我上面提供的链接*RFC6749*的第4.1节有详细阐述。）
     
    配置客户端详情信息（Client Details)：
    ClientDetailsServiceConfigurer (AuthorizationServerConfigurer 的一个回调配置项，见上的概述) 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService），有几个重要的属性如下列表：
    clientId：（必须的）用来标识客户的Id。
    secret：（需要值得信任的客户端）客户端安全码，如果有的话。
    scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
    authorizedGrantTypes：此客户端可以使用的授权类型，默认为空。
    authorities：此客户端可以使用的权限（基于Spring Security authorities）。
     
    客户端详情（Client Details）能够在应用程序运行的时候进行更新，可以通过访问底层的存储服务（例如将客户端详情存储在一个关系数据库的表中，就可以使用 JdbcClientDetailsService）或者通过 ClientDetailsManager 接口（同时你也可以实现 ClientDetailsService 接口）来进行管理。
    （译者注：不过我并没有找到 ClientDetailsManager 这个接口文件，只找到了 ClientDetailsService）
     
    管理令牌（Managing Token）：
    AuthorizationServerTokenServices 接口定义了一些操作使得你可以对令牌进行一些必要的管理，在使用这些操作的时候请注意以下几点：
    当一个令牌被创建了，你必须对其进行保存，这样当一个客户端使用这个令牌对资源服务进行请求的时候才能够引用这个令牌。
    当一个令牌是有效的时候，它可以被用来加载身份信息，里面包含了这个令牌的相关权限。
     
    当你自己创建 AuthorizationServerTokenServices 这个接口的实现时，你可能需要考虑一下使用 DefaultTokenServices 这个类，里面包含了一些有用实现，你可以使用它来修改令牌的格式和令牌的存储。默认的，当它尝试创建一个令牌的时候，是使用随机值来进行填充的，除了持久化令牌是委托一个 TokenStore 接口来实现以外，这个类几乎帮你做了所有的事情。并且 TokenStore 这个接口有一个默认的实现，它就是 InMemoryTokenStore ，如其命名，所有的令牌是被保存在了内存中。除了使用这个类以外，你还可以使用一些其他的预定义实现，下面有几个版本，它们都实现了TokenStore接口：
    InMemoryTokenStore：这个版本的实现是被默认采用的，它可以完美的工作在单服务器上（即访问并发量压力不大的情况下，并且它在失败的时候不会进行备份），大多数的项目都可以使用这个版本的实现来进行尝试，你可以在开发的时候使用它来进行管理，因为不会被保存到磁盘中，所以更易于调试。
    JdbcTokenStore：这是一个基于JDBC的实现版本，令牌会被保存进关系型数据库。使用这个版本的实现时，你可以在不同的服务器之间共享令牌信息，使用这个版本的时候请注意把"spring-jdbc"这个依赖加入到你的classpath当中。
    JwtTokenStore：这个版本的全称是 JSON Web Token（JWT），它可以把令牌相关的数据进行编码（因此对于后端服务来说，它不需要进行存储，这将是一个重大优势），但是它有一个缺点，那就是撤销一个已经授权令牌将会非常困难，所以它通常用来处理一个生命周期较短的令牌以及撤销刷新令牌（refresh_token）。另外一个缺点就是这个令牌占用的空间会比较大，如果你加入了比较多用户凭证信息。JwtTokenStore 不会保存任何数据，但是它在转换令牌值以及授权信息方面与 DefaultTokenServices 所扮演的角色是一样的。
     
     
    JWT令牌（JWT Tokens）：
    使用JWT令牌你需要在授权服务中使用 JwtTokenStore，资源服务器也需要一个解码的Token令牌的类 JwtAccessTokenConverter，JwtTokenStore依赖这个类来进行编码以及解码，因此你的授权服务以及资源服务都需要使用这个转换类。Token令牌默认是有签名的，并且资源服务需要验证这个签名，因此呢，你需要使用一个对称的Key值，用来参与签名计算，这个Key值存在于授权服务以及资源服务之中。或者你可以使用非对称加密算法来对Token进行签名，Public Key公布在/oauth/token_key这个URL连接中，默认的访问安全规则是"denyAll()"，即在默认的情况下它是关闭的，你可以注入一个标准的 SpEL 表达式到 AuthorizationServerSecurityConfigurer 这个配置中来将它开启（例如使用"permitAll()"来开启可能比较合适，因为它是一个公共密钥）。
     
    如果你要使用 JwtTokenStore，请务必把"spring-security-jwt"这个依赖加入到你的classpath中。
     
     
    配置授权类型（Grant Types）：
    授权是使用 AuthorizationEndpoint 这个端点来进行控制的，你能够使用 AuthorizationServerEndpointsConfigurer 这个对象的实例来进行配置(AuthorizationServerConfigurer 的一个回调配置项，见上的概述) ，如果你不进行设置的话，默认是除了资源所有者密码（password）授权类型以外，支持其余所有标准授权类型的（RFC6749），我们来看一下这个配置对象有哪些属性可以设置吧，如下列表：
    authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象。
    userDetailsService：如果啊，你设置了这个属性的话，那说明你有一个自己的 UserDetailsService 接口的实现，或者你可以把这个东西设置到全局域上面去（例如 GlobalAuthenticationManagerConfigurer 这个配置对象），当你设置了这个之后，那么 "refresh_token" 即刷新令牌授权类型模式的流程中就会包含一个检查，用来确保这个账号是否仍然有效，假如说你禁用了这个账户的话。
    authorizationCodeServices：这个属性是用来设置授权码服务的（即 AuthorizationCodeServices 的实例对象），主要用于 "authorization_code" 授权码类型模式。
    implicitGrantService：这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态。
    tokenGranter：这个属性就很牛B了，当你设置了这个东西（即 TokenGranter 接口实现），那么授权将会交由你来完全掌控，并且会忽略掉上面的这几个属性，这个属性一般是用作拓展用途的，即标准的四种授权模式已经满足不了你的需求的时候，才会考虑使用这个。
     
    在XML配置中呢，你可以使用 "authorization-server" 这个标签元素来进行设置。
     
     
    配置授权端点的URL（Endpoint URLs）：
    AuthorizationServerEndpointsConfigurer 这个配置对象(AuthorizationServerConfigurer 的一个回调配置项，见上的概述) 有一个叫做 pathMapping() 的方法用来配置端点URL链接，它有两个参数：
    第一个参数：String 类型的，这个端点URL的默认链接。
    第二个参数：String 类型的，你要进行替代的URL链接。
    以上的参数都将以 "/" 字符为开始的字符串，框架的默认URL链接如下列表，可以作为这个 pathMapping() 方法的第一个参数：
    /oauth/authorize：授权端点。
    /oauth/token：令牌端点。
    /oauth/confirm_access：用户确认授权提交端点。
    /oauth/error：授权服务错误信息端点。
    /oauth/check_token：用于资源服务访问的令牌解析端点。
    /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
     
    需要注意的是授权端点这个URL应该被Spring Security保护起来只供授权用户访问，我们来看看在标准的Spring Security中 WebSecurityConfigurer 是怎么用的。
     
    复制代码
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http .authorizeRequests().antMatchers("/login").permitAll().and()
        // default protection for all resources (including /oauth/authorize)
        .authorizeRequests() .anyRequest().hasRole("USER")
        // ... more configuration, e.g. for form login
    }
    复制代码
    注意：如果你的应用程序中既包含授权服务又包含资源服务的话，那么这里实际上是另一个的低优先级的过滤器来控制资源接口的，这些接口是被保护在了一个访问令牌（access token）中，所以请挑选一个URL链接来确保你的资源接口中有一个不需要被保护的链接用来取得授权，就如上面示例中的 /login 链接，你需要在 WebSecurityConfigurer 配置对象中进行设置。
     
    令牌端点默认也是受保护的，不过这里使用的是基于 HTTP Basic Authentication 标准的验证方式来验证客户端的，这在XML配置中是无法进行设置的（所以它应该被明确的保护）。
     
    在XML配置中可以使用 <authorization-server/> 元素标签来改变默认的端点URLs，注意在配置 /check_token 这个链接端点的时候，使用 check-token-enabled 属性标记启用。
     
     
    强制使用SSL（Enforcing SSL）：
    使用简单的HTTP请求来进行测试是可以的，但是如果你要部署到产品环境上的时候，你应该永远都使用SSL来保护授权服务器在与客户端进行通讯的时候进行加密。你可以把授权服务应用程序放到一个安全的运行容器中，或者你可以使用一个代理，如果你设置正确了的话它们应该工作的很好（这样的话你就不需要设置任何东西了）。
    但是也许你可能希望使用 Spring Security 的 requiresChannel() 约束来保证安全，对于授权端点来说（还记得上面的列表吗，就是那个 /authorize 端点），它应该成为应用程序安全连接的一部分，而对于 /token 令牌端点来说的话，它应该有一个标记被配置在 AuthorizationServerEndpointsConfigurer 配置对象中，你可以使用 sslOnly() 方法来进行设置。当然了，这两个设置是可选的，不过在以上两种情况中，会导致Spring Security 会把不安全的请求通道重定向到一个安全通道中。（译者注：即将HTTP请求重定向到HTTPS请求上）。
     
     
    自定义错误处理（Error Handling）：
    端点实际上就是一个特殊的Controller，它用于返回一些对象数据。
    授权服务的错误信息是使用标准的Spring MVC来进行处理的，也就是 @ExceptionHandler 注解的端点方法，你也可以提供一个 WebResponseExceptionTranslator 对象。最好的方式是改变响应的内容而不是直接进行渲染。
    假如说在呈现令牌端点的时候发生了异常，那么异常委托了 HttpMessageConverters 对象（它能够被添加到MVC配置中）来进行输出。假如说在呈现授权端点的时候未通过验证，则会被重定向到 /oauth/error 即错误信息端点中。whitelabel error （即Spring框架提供的一个默认错误页面）错误端点提供了HTML的响应，但是你大概可能需要实现一个自定义错误页面（例如只是简单的增加一个 @Controller 映射到请求路径上 @RequestMapping("/oauth/error")）。
     
     
    映射用户角色到权限范围（Mapping User Roles to Scopes）：
    有时候限制令牌的权限范围是很有用的，这不仅仅是针对于客户端，你还可以根据用户的权限来进行限制。如果你使用 DefaultOAuth2RequestFactory 来配置 AuthorizationEndpoint 的话你可以设置一个flag即 checkUserScopes=true来限制权限范围，不过这只能匹配到用户的角色。你也可以注入一个 OAuth2RequestFactory 到 TokenEnpoint 中，不过这只能工作在 password 授权模式下。如果你安装一个 TokenEndpointAuthenticationFilter 的话，你只需要增加一个过滤器到 HTTP BasicAuthenticationFilter 后面即可。当然了，你也可以实现你自己的权限规则到 scopes 范围的映射和安装一个你自己版本的 OAuth2RequestFactory。AuthorizationServerEndpointConfigurer 配置对象允许你注入一个你自定义的 OAuth2RequestFactory，因此你可以使用这个特性来设置这个工厂对象，前提是你使用 @EnableAuthorizationServer 注解来进行配置（见上面介绍的授权服务配置）。
     
     
     
    二、资源服务配置：
    一个资源服务（可以和授权服务在同一个应用中，当然也可以分离开成为两个不同的应用程序）提供一些受token令牌保护的资源，Spring OAuth提供者是通过Spring Security authentication filter 即验证过滤器来实现的保护，你可以通过 @EnableResourceServer 注解到一个 @Configuration 配置类上，并且必须使用 ResourceServerConfigurer 这个配置对象来进行配置（可以选择继承自 ResourceServerConfigurerAdapter 然后覆写其中的方法，参数就是这个对象的实例），下面是一些可以配置的属性：
    tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务。
    resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
    其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
    请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是受保护资源服务的全部路径。
    受保护资源的访问规则，默认的规则是简单的身份验证（plain authenticated）。
    其他的自定义权限保护规则通过 HttpSecurity 来进行配置。
     
    @EnableResourceServer 注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链，
     
    在XML配置中，使用 <resource-server />标签元素并指定id为一个servlet过滤器就能够手动增加一个标准的过滤器链。
     
    ResourceServerTokenServices 是组成授权服务的另一半，如果你的授权服务和资源服务在同一个应用程序上的话，你可以使用 DefaultTokenServices ，这样的话，你就不用考虑关于实现所有必要的接口的一致性问题，这通常是很困难的。如果你的资源服务器是分离开的，那么你就必须要确保能够有匹配授权服务提供的 ResourceServerTokenServices，它知道如何对令牌进行解码。
    在授权服务器上，你通常可以使用 DefaultTokenServices 并且选择一些主要的表达式通过 TokenStore（后端存储或者本地编码）。
    RemoteTokenServices 可以作为一个替代，它将允许资源服务器通过HTTP请求来解码令牌（也就是授权服务的 /oauth/check_token 端点）。如果你的资源服务没有太大的访问量的话，那么使用RemoteTokenServices 将会很方便（所有受保护的资源请求都将请求一次授权服务用以检验token值），或者你可以通过缓存来保存每一个token验证的结果。
    使用授权服务的 /oauth/check_token 端点你需要将这个端点暴露出去，以便资源服务可以进行访问，这在咱们授权服务配置中已经提到了，下面是一个例子：
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')")
            .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
    }
     
    
    在这个例子中，我们配置了 /oauth/check_token 和 /oauth/token_key 这两个端点（受信任的资源服务能够获取到公有密匙，这是为了验证JWT令牌）。这两个端点使用了HTTP Basic Authentication 即HTTP基本身份验证，使用 client_credentials 授权模式可以做到这一点。
     
     
    配置OAuth-Aware表达式处理器（OAuth-Aware Expression Handler）：
    你也许希望使用 Spring Security's expression-based access control 来获得一些优势，一个表达式处理器会被注册到默认的 @EnableResourceServer 配置中，这个表达式包含了 #oauth2.clientHasRole，#oauth2.clientHasAnyRole 以及 #oauth2.denyClient 所提供的方法来帮助你使用权限角色相关的功能（在 OAuth2SecurityExpressionMethods 中有完整的列表）。
    在XML配置中你可以注册一个 OAuth-Aware 表达式处理器即 <expression-handler />元素标签到 常规的 <http /> 安全配置上。
    

###配置实例
----
######AuthorizationServerConfigurerAdapter中:

*ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。<br>
*AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束.<br>
*AuthorizationServerEndpointsConfigurer：用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。<br>

ClientDetailsServiceConfigurer (AuthorizationServerConfigurer 的一个回调配置项) 能够使用内存或者JDBC来实现客户端详情服务（ClientDetailsService），Spring Security OAuth2的配置方法是编写@Configuration类继承AuthorizationServerConfigurerAdapter，然后重写void configure(ClientDetailsServiceConfigurer clients)方法，如：

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }
    
######配置令牌 管理 (jwtAccessTokenConverter)
JwtAccessTokenConverter是用来生成token的转换器，而token令牌默认是有签名的，且资源服务器需要验证这个签名。此处的加密及验签包括两种方式：<br>
对称加密、非对称加密（公钥密钥）<br>
对称加密需要授权服务器和资源服务器存储同一key值，而非对称加密可使用密钥加密，暴露公钥给资源服务器验签，本文中使用非对称加密方式，配置于AuthorizationServerConfigurerAdapter如下：<br>

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                // 配置JwtAccessToken转换器
                .accessTokenConverter(jwtAccessTokenConverter())
                // refresh_token需要userDetailsService
                .reuseRefreshTokens(false).userDetailsService(userDetailsService);
                //.tokenStore(getJdbcTokenStore());
    }

    /**
     * 使用非对称加密算法来对Token进行签名
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));

        return converter;
    }
    
通过 JDK 工具生成 JKS 证书文件，并将 keystore.jks 放入resource目录下
    
    keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore keystore.jks -storepass mypass
    
此处我们自定义JwtAccessToken用于添加额外用户信息    
    
    /**
     * Created by fp295 on 2018/4/16.
     * 自定义JwtAccessToken转换器
     */
    public class JwtAccessToken extends JwtAccessTokenConverter {
    
        /**
         * 生成token
         * @param accessToken
         * @param authentication
         * @return
         */
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
    
            // 设置额外用户信息
            BaseUser baseUser = ((BaseUserDetail) authentication.getPrincipal()).getBaseUser();
            baseUser.setPassword(null);
            // 将用户信息添加到token额外信息中
            defaultOAuth2AccessToken.getAdditionalInformation().put(Constant.USER_INFO, baseUser);
    
            return super.enhance(defaultOAuth2AccessToken, authentication);
        }
    
        /**
         * 解析token
         * @param value
         * @param map
         * @return
         */
        @Override
        public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
            OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
            convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
            return oauth2AccessToken;
        }
    
        private void convertData(OAuth2AccessToken accessToken,  Map<String, ?> map) {
            accessToken.getAdditionalInformation().put(Constant.USER_INFO,convertUserData(map.get(Constant.USER_INFO)));
    
        }
    
        private BaseUser convertUserData(Object map) {
            String json = JsonUtils.deserializer(map);
            BaseUser user = JsonUtils.serializable(json, BaseUser.class);
            return user;
        }
    }
    
JwtAccessToken 类中从authentication里的getPrincipal（实际为UserDetails接口）获取用户信息，所以我们需要实现自己的UserDetails

    /**
     * Created by fp295 on 2018/4/29.
     * 包装org.springframework.security.core.userdetails.User类
     */
    public class BaseUserDetail implements UserDetails, CredentialsContainer {
    
        private final BaseUser baseUser;
        private final org.springframework.security.core.userdetails.User user;
    
        public BaseUserDetail(BaseUser baseUser, User user) {
            this.baseUser = baseUser;
            this.user = user;
        }
    
    
        @Override
        public void eraseCredentials() {
            user.eraseCredentials();
        }
    
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return user.getAuthorities();
        }
    
        @Override
        public String getPassword() {
            return user.getPassword();
        }
    
        @Override
        public String getUsername() {
            return user.getUsername();
        }
    
        @Override
        public boolean isAccountNonExpired() {
            return user.isAccountNonExpired();
        }
    
        @Override
        public boolean isAccountNonLocked() {
            return user.isAccountNonLocked();
        }
    
        @Override
        public boolean isCredentialsNonExpired() {
            return user.isCredentialsNonExpired();
        }
    
        @Override
        public boolean isEnabled() {
            return user.isEnabled();
        }
    
        public BaseUser getBaseUser() {
            return baseUser;
        }
    }
    
授权端点开放

     @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer
                    // 开启/oauth/token_key验证端口无权限访问
                    .tokenKeyAccess("permitAll()")
                    // 开启/oauth/check_token验证端口认证权限访问
                    .checkTokenAccess("isAuthenticated()");
        }
        
####Security 配置

---
需要配置 DaoAuthenticationProvider、UserDetailService 等


    @Configuration
    @Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
        // 自动注入UserDetailsService
        @Autowired
        private BaseUserDetailService baseUserDetailService;
    
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http    // 配置登陆页/login并允许访问
                    .formLogin().permitAll()
                    // 登出页
                    .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                    // 其余所有请求全部需要鉴权认证
                    .and().authorizeRequests().anyRequest().authenticated()
                    // 由于使用的是JWT，我们这里不需要csrf
                    .and().csrf().disable();
        }
    
        /**
         * 用户验证
         * @param auth
         */
        @Override
        public void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(daoAuthenticationProvider());
        }
    
    
        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider(){
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            // 设置userDetailsService
            provider.setUserDetailsService(baseUserDetailService);
            // 禁止隐藏用户未找到异常
            provider.setHideUserNotFoundExceptions(false);
            // 使用BCrypt进行密码的hash
            provider.setPasswordEncoder(new BCryptPasswordEncoder(6));
            return provider;
        }
    }
    
######UserDetailsService 实现    

    @Service
    public class BaseUserDetailService implements UserDetailsService {
    
        private Logger logger = LoggerFactory.getLogger(this.getClass());
    
        @Autowired
        private BaseUserService baseUserService;
        @Autowired
        private BaseRoleService baseRoleService;
     
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
            // 调用FeignClient查询用户
            ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByUserName(username);
            if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
                logger.error("找不到该用户，用户名：" + username);
                throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
            }
            BaseUser baseUser = baseUserResponseData.getData();
    
            // 调用FeignClient查询角色
            ResponseData<List<BaseRole>> baseRoleListResponseData = baseRoleService.getRoleByUserId(baseUser.getId());
            List<BaseRole> roles;
            if(baseRoleListResponseData.getData() == null ||  !ResponseCode.SUCCESS.getCode().equals(baseRoleListResponseData.getCode())){
                logger.error("查询角色失败！");
                roles = new ArrayList<>();
            }else {
                roles = baseRoleListResponseData.getData();
            }
    
            // 获取用户权限列表
            List<GrantedAuthority> authorities = new ArrayList();
            roles.forEach(e -> {
                // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
                GrantedAuthority authority = new SimpleGrantedAuthority(e.getRoleCode());
                authorities.add(authority);
            
            });
    
            // 返回带有用户权限信息的User
            org.springframework.security.core.userdetails.User user =  new org.springframework.security.core.userdetails.User(baseUser.getUserName(),
                    baseUser.getPassword(), isActive(baseUser.getActive()), true, true, true, authorities);
            return new BaseUserDetail(baseUser, user);
        }
    
        private boolean isActive(int active){
            return active == 1 ? true : false;
        }
    }

授权服务器验证
http://127.0.0.1:8080/oauth/authorize?client_id=clientId&response_type=code&redirect_uri=www.baidu.com
    
    注意：client_id：为存储在数据库里的client_id, response_type：写死code
    
    链接回车后进入spring security 的简单登陆页面,
    
    
    输入账号密码，为实现的 UserDetailsService 要里获取的用户，点击 login,
    进入简单授权页面，点击 Authorize,
    重定向到 redirect_uri，并带有 code 参数:
    http://www.baidu.com?code=rTKETX
    post请求获取 token: