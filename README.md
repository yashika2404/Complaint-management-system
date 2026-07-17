# Complaint Management System (Simple Version)

No JWT, no Spring Security — session-based login, seedha samajh aane wala code.

## Stack
Java 17, Spring Boot 3.3, Spring Data JPA, MySQL, Thymeleaf (server-side HTML), Bootstrap (CDN se, koi npm install nahi chahiye), Maven.

## Kaise chalayein

### 1. MySQL check karo
MySQL local mein chal raha hona chahiye (XAMPP / MySQL Workbench / direct install — jo bhi use karti ho). Database khud ban jayega, kuch manually create karne ki zaroorat nahi.

### 2. `application.properties` edit karo
File: `src/main/resources/application.properties`
```
spring.datasource.username=root
spring.datasource.password=your_mysql_password   <-- yaha apna actual password daalo
```

### 3. Project run karo
Terminal mein project folder ke andar:
```
mvn spring-boot:run
```
Ya IntelliJ/Eclipse mein `ComplaintSimpleApplication.java` pe right-click → Run.

### 4. Browser mein kholo
```
http://localhost:8080
```
Ye seedha `/login` page pe le jayega.

## Kaise use karein
1. **Register** karo 3 alag users se — ek USER role, ek STAFF role, ek ADMIN role select karke.
2. **USER** se login karke complaint daalo.
3. **ADMIN** se login karke complaint ko STAFF ko assign karo.
4. **STAFF** se login karke us complaint ka status update karo (In Progress / Resolved / Closed).

## Code kaise samjhein (flow)

```
Browser (HTML form submit)
      ↓
Controller (request receive karta hai, @PostMapping/@GetMapping)
      ↓
Service (business logic — jaise priority auto-decide karna)
      ↓
Repository (JpaRepository — database se baat karta hai)
      ↓
Entity (User.java, Complaint.java — ye hi database table ban jaate hain)
```

- **Entity** = ek Java class jo database table represent karti hai (`@Entity` annotation).
- **Repository** = interface jisme kuch bhi likhna nahi padta, Spring Data JPA khud implement kar deta hai (`findByEmail` jaisa method naam likho, wo khud query bana lega).
- **Service** = jaha actual logic likhte hain (jaise priority calculate karna).
- **Controller** = jo URL hit hone par sahi HTML page dikhata hai ya form data process karta hai.
- **Session** (`HttpSession`) = login hone ke baad user ka ID browser ke session mein store rehta hai, taaki har page pe pata chale kaun login hai. Jaise ek "temporary memory" jab tak browser tab khula hai.

## Limitations (jaan-bujh kar simple rakha hai)
- Password plain text mein store ho raha hai (production mein kabhi mat karna — BCrypt use hota hai, seekhne ke baad add karna easy hai)
- Koi email validation / duplicate role checks nahi hain
- Image upload nahi hai abhi
- Ek user hi apni complaint dekh sakta hai, staff sirf apni assigned complaints

## Next steps (jab ye samajh aa jaye)
1. Password ko BCrypt se hash karna seekho
2. Email notification add karo (complaint create/resolve hone par)
3. Search/filter add karo (status, category ke hisaab se)
4. Image upload add karo complaints ke liye
5. Phir chaho to Spring Security + JWT wala proper version try karna — tab samajh aayega ki wo kya solve karta hai
