#Spring Security  原理详解<br>
>众所周知 想要对对Web资源进行保护，最好的办法莫过于Filter,
要想对方法调用进行保护，最好的办法莫过于AOP.
所以springSecurity在我们进行用户认证以及授予权限的时候，
通过各种各样的拦截器来控制权限的访问，从而实现安全

###Spring Security的几个核心组件<br>
####**如下为其主要过滤器:<br>**
1.WebAsyncManagerIntegrationFilter <br>
2.HeaderWriterFilter <br>
3.CorsFilter <br>
4.LogoutFilter<br>
5.RequestCacheAwareFilter<br>
5.SecurityContextHolderAwareRequestFilter<br>
7.AnonymousAuthenticationFilter<br>
8.SessionManagementFilter<br>
9.ExceptionTranslationFilter<br>
10.FilterSecurityInterceptor<br>
11.UsernamePasswordAuthenticationFilter<br>
12.BasicAuthenticationFilter<br>

####**框架的核心组件**
1.SecurityContextHolder：提供对SecurityContext的访问<br>
2.SecurityContext,：持有Authentication对象和其他可能需要的信息<br>
3.AuthenticationManager 其中可以包含多个AuthenticationProvider<br>
4.ProviderManager对象为AuthenticationManager接口的实现类<br>
5.AuthenticationProvider 主要用来进行认证操作的类 
调用其中的authenticate()方法去进行认证操作<br>
6.Authentication：Spring Security方式的认证主体<br>
7.GrantedAuthority：对认证主题的应用层面的授权，含当前用户的权限信息，通常使用角色表示<br>
8.UserDetails：构建Authentication对象必须的信息，可以自定义，可能需要访问DB得到<br>
9.UserDetailsService：通过username构建UserDetails对象，通过loadUserByUsername
					  根据userName获取UserDetail对象 （可以在这里基于自身业务进行自定义的实现 
					  如通过数据库，xml,缓存获取等）<br>
####**自定义安全配置的加载机制**
    @Configuration
    public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
     
        @Autowired
        private AccessDeniedHandler accessDeniedHandler;
     
        @Autowired
        private CustAuthenticationProvider custAuthenticationProvider;
        //重写了其中的configure（）方法设置了不同url的不同访问权限
        @Override
        protected void configure(HttpSecurity http) throws Exception {
           //todo 对http 和filter的一些设置
        }
     
        // create two users, admin and user
        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //        auth.inMemoryAuthentication()
    //                .withUser("user").password("user").roles("USER")
    //                .and()
    //                .withUser("admin").password("admin").roles("ADMIN");
    //        auth.jdbcAuthentication()
    //应该时自定义验证方法的一些配置
            auth.authenticationProvider(custAuthenticationProvider);
        }
        
**其实在我们实现该类后，在web容器启动的过程中该类实例对象会被WebSecurityConfiguration类处理**
    
    @Configuration
    public class WebSecurityConfiguration implements ImportAware, BeanClassLoaderAware {
        private WebSecurity webSecurity;
        private Boolean debugEnabled;
        private List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers;
        private ClassLoader beanClassLoader;
       
       ...省略部分代码
   
        @Bean(
            name = {"springSecurityFilterChain"}
        )
            /* 1、创建webSecurity对象
             2、主要检验了配置实例的order顺序（order唯一 否则会报错）
             3、将所有的配置实例存放进入到webSecurity对象中，其中配置实例中含有我们自定义业务的权限控制配置信息*/
        public Filter springSecurityFilterChain() throws Exception {
            boolean hasConfigurers = this.webSecurityConfigurers != null
             && !this.webSecurityConfigurers.isEmpty();
            if(!hasConfigurers) {
                WebSecurityConfigurerAdapter adapter = (WebSecurityConfigurerAdapter)
                this.objectObjectPostProcessor
                  .postProcess(new WebSecurityConfigurerAdapter() {
                });
                this.webSecurity.apply(adapter);
            }
     
            return (Filter)this.webSecurity.build();
        }
        /*1、先执行该方法将我们自定义springSecurity配置实例（可能还有系统默认的有关安全的配置实例 ）
            配置实例中含有我们自定义业务的权限控制配置信息
            放入到该对象的list数组中webSecurityConfigurers中
            使用@Value注解来将实例对象作为形参注入
         */   
         @Autowired(
            required = false
        )
        public void setFilterChainProxySecurityConfigurer(ObjectPostProcessor<Object> 
        objectPostProcessor,
        @Value("#{@autowiredWebSecurityConfigurersIgnoreParents.getWebSecurityConfigurers()}") 
        List<SecurityConfigurer<Filter, WebSecurity>> webSecurityConfigurers) 
        throws Exception {
        
        //创建一个webSecurity对象    
        this.webSecurity = (WebSecurity)objectPostProcessor.postProcess(new WebSecurity(objectPostProcessor));
            if(this.debugEnabled != null) {
                this.webSecurity.debug(this.debugEnabled.booleanValue());
            }
     
            //对所有配置类的实例进行排序
            Collections.sort(webSecurityConfigurers, WebSecurityConfiguration.AnnotationAwareOrderComparator.INSTANCE);
            Integer previousOrder = null;
            Object previousConfig = null;
     
     
            //迭代所有配置类的实例 判断其order必须唯一
            Iterator var5;
            SecurityConfigurer config;
            for(var5 = webSecurityConfigurers.iterator(); var5.hasNext(); previousConfig = config) {
                config = (SecurityConfigurer)var5.next();
                Integer order = Integer.valueOf(WebSecurityConfiguration.AnnotationAwareOrderComparator.lookupOrder(config));
                if(previousOrder != null && previousOrder.equals(order)) {
                    throw new IllegalStateException("@Order on WebSecurityConfigurers must be unique. Order of " + order + " was already used on " + previousConfig + ", so it cannot be used on " + config + " too.");
                }
     
                previousOrder = order;
            }
     
     
            //将所有的配置实例添加到创建的webSecutity对象中
            var5 = webSecurityConfigurers.iterator();
     
            while(var5.hasNext()) {
                config = (SecurityConfigurer)var5.next();
                this.webSecurity.apply(config);
            }
            //将webSercurityConfigures 实例放入该对象的webSecurityConfigurers属性中
            this.webSecurityConfigurers = webSecurityConfigurers;
        }
 调用springSecurityFilterChain()方法，这个方法会判断我们上一个方法中有没有获取到webSecurityConfigurers，
 没有的话这边会创建一个WebSecurityConfigurerAdapter实例，并追加到websecurity中。
 接着调用websecurity的build方法。实际调用的是websecurity的父类
 AbstractSecurityBuilder的build方法 ，最终返回一个名称为springSecurityFilterChain
 的过滤器链。里面有众多Filter(springSecurity其实就是依靠很多的Filter来拦截url从而实现权限的控制的安全框架)

    public abstract class AbstractSecurityBuilder<O> implements SecurityBuilder<O> {
          private AtomicBoolean building = new AtomicBoolean();
          private O object;
          //调用build方法来返回过滤器链，还是调用SecurityBuilder的dobuild()方法
          public final O build() throws Exception {
              if(this.building.compareAndSet(false, true)) {
                  this.object = this.doBuild();
                  return this.object;
              } else {
                  throw new AlreadyBuiltException("This object has already been built");
              }
          }
         //...省略部分代码
      }
3.1 调用子类的doBuild()方法

    public abstract class AbstractConfiguredSecurityBuilder<O, B extends SecurityBuilder<O>> extends AbstractSecurityBuilder<O> {
        private final Log logger;
        private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers;
        private final List<SecurityConfigurer<O, B>> configurersAddedInInitializing;
        private final Map<Class<? extends Object>, Object> sharedObjects;
        private final boolean allowConfigurersOfSameType;
        private AbstractConfiguredSecurityBuilder.BuildState buildState;
        private ObjectPostProcessor<Object> objectPostProcessor;
     
        //doBuild()核心方法 init(),configure(),perFormBuild()
        protected final O doBuild() throws Exception {
            LinkedHashMap var1 = this.configurers;
            synchronized(this.configurers) {
                this.buildState = AbstractConfiguredSecurityBuilder.BuildState.INITIALIZING;
                this.beforeInit();
                this.init();
                this.buildState = AbstractConfiguredSecurityBuilder.BuildState.CONFIGURING;
                this.beforeConfigure();
                this.configure();
                this.buildState = AbstractConfiguredSecurityBuilder.BuildState.BUILDING;
                O result = this.performBuild();
                this.buildState = AbstractConfiguredSecurityBuilder.BuildState.BUILT;
                return result;
            }
        }
     
        protected abstract O performBuild() throws Exception;
        
        //调用init方法 调用配置类WebSecurityConfigurerAdapter的init()方法
        private void init() throws Exception {
            Collection<SecurityConfigurer<O, B>> configurers = this.getConfigurers();
            Iterator var2 = configurers.iterator();
     
            SecurityConfigurer configurer;
            while(var2.hasNext()) {
                configurer = (SecurityConfigurer)var2.next();
                configurer.init(this);
            }
     
            var2 = this.configurersAddedInInitializing.iterator();
     
            while(var2.hasNext()) {
                configurer = (SecurityConfigurer)var2.next();
                configurer.init(this);
            }
     
        }
     
        private void configure() throws Exception {
            Collection<SecurityConfigurer<O, B>> configurers = this.getConfigurers();
            Iterator var2 = configurers.iterator();
     
            while(var2.hasNext()) {
                SecurityConfigurer<O, B> configurer = (SecurityConfigurer)var2.next();
                configurer.configure(this);
            }
     
        }
     
        private Collection<SecurityConfigurer<O, B>> getConfigurers() {
            List<SecurityConfigurer<O, B>> result = new ArrayList();
            Iterator var2 = this.configurers.values().iterator();
     
            while(var2.hasNext()) {
                List<SecurityConfigurer<O, B>> configs = (List)var2.next();
                result.addAll(configs);
            }
     
            return result;
        }
     
        //...省略部分代码
    }
**先调用本类的init()方法<br>**
build过程主要分三步，init->configure->peformBuild 
* init方法做了两件事，一个就是调用getHttp()方法获取一个http实例，
  并通过web.addSecurityFilterChainBuilder方法把获取到的实例赋值给WebSecurity
  的securityFilterChainBuilders属性，这个属性在我们执行build的时候会用到，
  第二个就是为WebSecurity追加了一个postBuildAction，
  在build都完成后从http中拿出FilterSecurityInterceptor对象并赋值给WebSecurity。 
* getHttp()方法，这个方法在当我们使用默认配置时（大多数情况下）会为我们追加各种
   SecurityConfigurer的具体实现类到httpSecurity中，如exceptionHandling()
   方法会追加一个ExceptionHandlingConfigurer，sessionManagement()
   方法会追加一个SessionManagementConfigurer,securityContext()
   方法会追加一个SecurityContextConfigurer对象，这些SecurityConfigurer
   的具体实现类最终会为我们配置各种具体的filter。
* 另外getHttp()方法的最后会调用configure(http)，
  这个方法也是我们继承WebSecurityConfigurerAdapter类后最可能会重写的方法 。
* configure(HttpSecurity http)方法，默认的configure(HttpSecurity http)
  方法继续向httpSecurity类中追加SecurityConfigurer的具体实现类，
  如authorizeRequests()方法追加一个ExpressionUrlAuthorizationConfigurer，
  formLogin()方法追加一个FormLoginConfigurer。 
  其中ExpressionUrlAuthorizationConfigurer这个实现类比较重要，
  因为他会给我们创建一个非常重要的对象FilterSecurityInterceptor对象，
  FormLoginConfigurer对象比较简单，但是也会为我们提供一个在安全认证过程中经常用到会用的一个
  Filter：UsernamePasswordAuthenticationFilter。 

以上三个方法就是WebSecurityConfigurerAdapter类中init方法：<br>
 ####**第一步**

    public abstract class WebSecurityConfigurerAdapter implements 
       WebSecurityConfigurer<WebSecurity> {
        public void init(final WebSecurity web) throws Exception {
            final HttpSecurity http = this.getHttp();
            web.addSecurityFilterChainBuilder(http).postBuildAction(new Runnable() {
                public void run() {
                    FilterSecurityInterceptor securityInterceptor = (FilterSecurityInterceptor)http.getSharedObject(FilterSecurityInterceptor.class);
                    web.securityInterceptor(securityInterceptor);
                }
            });
        }
     
     protected final HttpSecurity getHttp() throws Exception {
            if(this.http != null) {
                return this.http;
            } else {
                DefaultAuthenticationEventPublisher eventPublisher = (DefaultAuthenticationEventPublisher)this.objectPostProcessor.postProcess(new DefaultAuthenticationEventPublisher());
           
     //添加认证的事件的发布者
    this.localConfigureAuthenticationBldr.authenticationEventPublisher(eventPublisher);
    //获取AuthenticationManager对象其中一至多个进行认证处理的对象实例，后面会进行讲解          
    AuthenticationManager authenticationManager = this.authenticationManager();
                this.authenticationBuilder.parentAuthenticationManager(authenticationManager);
                Map<Class<? extends Object>, Object> sharedObjects = this.createSharedObjects();
                this.http = new HttpSecurity(this.objectPostProcessor, this.authenticationBuilder, sharedObjects);
                if(!this.disableDefaults) {
                    ((HttpSecurity)((DefaultLoginPageConfigurer)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)((HttpSecurity)this.http.csrf().and()).addFilter(new WebAsyncManagerIntegrationFilter()).exceptionHandling().and()).headers().and()).sessionManagement().and()).securityContext().and()).requestCache().and()).anonymous().and()).servletApi().and()).apply(new DefaultLoginPageConfigurer())).and()).logout();
                    ClassLoader classLoader = this.context.getClassLoader();
                    List<AbstractHttpConfigurer> defaultHttpConfigurers = SpringFactoriesLoader.loadFactories(AbstractHttpConfigurer.class, classLoader);
                    Iterator var6 = defaultHttpConfigurers.iterator();
     
                    while(var6.hasNext()) {
                        AbstractHttpConfigurer configurer = (AbstractHttpConfigurer)var6.next();
                        this.http.apply(configurer);
                    }
                }
                //最终调用我们的继承的WebSecurityConfigurerAdapter中重写的configure()
                //将我们业务相关的权限配置规则信息进行初始化操作
                this.configure(this.http);
                return this.http;
            }
        }
        
     protected AuthenticationManager authenticationManager() throws Exception {
            if(!this.authenticationManagerInitialized) {
                this.configure(this.localConfigureAuthenticationBldr);
                if(this.disableLocalConfigureAuthenticationBldr) {
                    this.authenticationManager = this.authenticationConfiguration.getAuthenticationManager();
                } else {
                    this.authenticationManager = (AuthenticationManager)this.localConfigureAuthenticationBldr.build();
                }
                this.authenticationManagerInitialized = true;
            }
            return this.authenticationManager;
        }
     }
####**第二步**
 configure方法最终也调用到了WebSecurityConfigurerAdapter的configure(WebSecurity web)方法，默认实现中这个是一个空方法，具体应用中也经常重写这个方法来实现特定需求。
    
####**第三步** peformBuild
* 具体的实现逻辑在WebSecurity类中 
* 这个方法中最主要的任务就是遍历securityFilterChainBuilders属性中的SecurityBuilder对象，并调用他的build方法。 
 这个securityFilterChainBuilders属性我们前面也有提到过，就是在WebSecurityConfigurerAdapter类的init方法中获取http后赋值给了WebSecurity。因此这个地方就是调用httpSecurity的build方法。
* httpSecurity的build方式向其中追加一个个过滤器


    public final class WebSecurity extends AbstractConfiguredSecurityBuilder<Filter, WebSecurity> implements SecurityBuilder<Filter>, ApplicationContextAware {
        
      ...省略部分代码
     
        //调用该方法通过securityFilterChainBuilder.build()方法来创建securityFilter过滤器
        //并添加到securityFilterChains对象中，包装成FilterChainProxy 返回
        protected Filter performBuild() throws Exception {
            Assert.state(!this.securityFilterChainBuilders.isEmpty(), "At least one SecurityBuilder<? extends SecurityFilterChain> needs to be specified. Typically this done by adding a @Configuration that extends WebSecurityConfigurerAdapter. More advanced users can invoke " + WebSecurity.class.getSimpleName() + ".addSecurityFilterChainBuilder directly");
            int chainSize = this.ignoredRequests.size() + this.securityFilterChainBuilders.size();
            List<SecurityFilterChain> securityFilterChains = new ArrayList(chainSize);
            Iterator var3 = this.ignoredRequests.iterator();
     
            while(var3.hasNext()) {
                RequestMatcher ignoredRequest = (RequestMatcher)var3.next();
                securityFilterChains.add(new DefaultSecurityFilterChain(ignoredRequest, new Filter[0]));
            }
     
            var3 = this.securityFilterChainBuilders.iterator();
     
            while(var3.hasNext()) {
                SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder = (SecurityBuilder)var3.next();
                securityFilterChains.add(securityFilterChainBuilder.build());
            }
     
            FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
            if(this.httpFirewall != null) {
                filterChainProxy.setFirewall(this.httpFirewall);
            }
     
            filterChainProxy.afterPropertiesSet();
            Filter result = filterChainProxy;
            if(this.debugEnabled) {
                this.logger.warn("\n\n********************************************************************\n**********        Security debugging is enabled.       *************\n**********    This may include sensitive information.  *************\n**********      Do not use in a production system!     *************\n********************************************************************\n\n");
                result = new DebugFilter(filterChainProxy);
            }
     
            this.postBuildAction.run();
            return (Filter)result;
        }
#### 如何将一个Configurer转换为filter

ExpressionUrlAuthorizationConfigurer的继承关系<br>
ExpressionUrlAuthorizationConfigurer->AbstractInterceptUrlConfigurer->AbstractHttpConfigurer->SecurityConfigurerAdapter->SecurityConfigurer <br>
对应的init方法在SecurityConfigurerAdapter类中，是个空实现，什么也没有做，configure方法在SecurityConfigurerAdapter类中也有一个空实现，在AbstractInterceptUrlConfigurer类中进行了重写 <br>

    @Override  
        public void configure(H http) throws Exception {  
            FilterInvocationSecurityMetadataSource metadataSource = createMetadataSource(http);  
            if (metadataSource == null) {  
                return;  
            }  
            FilterSecurityInterceptor securityInterceptor = createFilterSecurityInterceptor(  
                    http, metadataSource, http.getSharedObject(AuthenticationManager.class));  
            if (filterSecurityInterceptorOncePerRequest != null) {  
                securityInterceptor  
                        .setObserveOncePerRequest(filterSecurityInterceptorOncePerRequest);  
            }  
            securityInterceptor = postProcess(securityInterceptor);  
            http.addFilter(securityInterceptor);  
            http.setSharedObject(FilterSecurityInterceptor.class, securityInterceptor);  
        }  
    ...  
    private AccessDecisionManager createDefaultAccessDecisionManager(H http) {  
            AffirmativeBased result = new AffirmativeBased(getDecisionVoters(http));  
            return postProcess(result);  
        }  
    ...  
    private FilterSecurityInterceptor createFilterSecurityInterceptor(H http,  
                FilterInvocationSecurityMetadataSource metadataSource,  
                AuthenticationManager authenticationManager) throws Exception {  
            FilterSecurityInterceptor securityInterceptor = new FilterSecurityInterceptor();  
            securityInterceptor.setSecurityMetadataSource(metadataSource);  
            securityInterceptor.setAccessDecisionManager(getAccessDecisionManager(http));  
            securityInterceptor.setAuthenticationManager(authenticationManager);  
            securityInterceptor.afterPropertiesSet();  
            return securityInterceptor;  
        }  
在这个类的configure中创建了一个FilterSecurityInterceptor，并且也可以明确看到spring security默认给我们创建的AccessDecisionManager是AffirmativeBased<br>
最后再看下HttpSecurity类执行build的最后一步 performBuild，这个方法就是在HttpSecurity中实现的<br>
