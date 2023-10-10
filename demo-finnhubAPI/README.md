1.0.1 -> Version 版本-> api/v1 -> 連唔到version

1. @Controller + @ResponseBody (@RestController)
  - Bean + Web Layer
2. @Service
  - Bean
3. @Repository (JPA + Hibernate)
  - Define Entity (with @Id & implements Serializable)
  - Bean
  - JPA includes basic CRUD operations
  - method name rules for Hibernate generating implementions
  - @Query -> JPQL (Entity) or Native SQL
  - nativeQuery = True
  - Isolate the Database implementation and Hiberate (MySQL, Oracle)
4. @Configuration (Class Annotation)
  - @Bean (Method Annotation) -> Create Bean by Method
5. What is Depenedency Injection (DI)?
  - @Autowird
    - field injection (@Autowird on a field)
    - constructor injection (@Autowird on a constructor)
  - Controller depends on Service (because controller autowired service)
  - AppConfig class depends on yml (for example, it used @Value)
6. What is IoC (Inversion of Control)
  - Java: use new keyword to create object. You are the only one to control the relationship between objects.
  - Spring or Spring Boot: Application Context plays a role of managing the depenedency between objects. It complains during th server starts if it found any missing dependency.
7. RestTemplate
  - getForObject
  - UriComponentsBuiler (with yml, @Value)
  - Define the return type (Object or Array)
8. model class (DTO)
  - lombok
  - ModelMapper
  - mapper class
9. @Scheduled
  - @FixRated, @FixDelay, @Scheduled (cron = ?)
10. CommandLineRunner (Interface)
  - @Component
  - implements run method
  - this method will be executed during server start
  - Server start will fail if the run method fail
11. ApiResp<T> 
  - generics of data
12. Custom Exception class (extends Exception.class)
  - BusinesException
12. GlobalExceptionHandler
  - @ControllerAdvise (@RestControllerAdvise)
  - @ExceptionHandler (method)
  - Catch from child to parent (includes runtime, checked exception)
13. DTO
  - Deserialization (Controller RequestBody: from JSON to Object)
  - Serialization (Controller ResponseBody: from Object to JSON)
  - ObjectMapper (test code)
14. test code
  - By Environment & Layer
    - @Test, @SpringBootTest
    - Web Layer 
      - @WebMvcTest (@Controller Only)
      - Autowird MockMvc
      - @MockBean for Service (Controller Autowird Service)
      - Mockito, when & thenReturn for MockBean's method
      - mockMvc.perform() -> test JSON structure
      - verify if service layer being called
    - Service Layer
      - @SpringBootTest
      - @MockBean for Repository (Servie autowird Repository)
      - @Mock, @InjectMock -> mock normal java class & method
      - Mockito, when & thenReturn for MockBean's method
      - Hamcrest (assertThat)
    - Repository Layer
      - @DataJpaLayer
      - Autowird TestEntityManager
      - Autowird repository
      - TestEntityManager.persist() -> prepare testing data
      - repository.save(), findById() -> test Hibernate