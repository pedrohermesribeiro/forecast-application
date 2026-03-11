package com.forecastapp.auth_service.userClient;

//import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.forecastapp.auth_service.model.UserDTO;
//import com.forecastapp.auth_service.security.JwtTokenUtil;

//import io.netty.channel.unix.Socket;

//import io.netty.handler.codec.http.HttpHeaders;

@Service
public class UserClient {

    //@Autowired
    //private RestTemplate restTemplate;

    //@Autowired
    //private JwtTokenUtil jwtTokenUtil;

    /*public UserDTO findByEmail(String email) {
        String url = "http://user_service:8080/users/search?email={email}";
        System.out.println("➡️ Chamando URL: " + url);
        UserDTO  userDTO = restTemplate.getForObject(url, UserDTO.class,email);
        
        return userDTO;
    }*/

        /*

    public UserDTO findByEmail(String email) {

        //String apiUrl = "http://localhost:8083/users";
        Long id = 1L;
        String baseUrl = "http://localhost:8080/users/{id}";

        String url = String.format("%s?id=%s", baseUrl,id);

        //String token = jwtTokenUtil.generateToken(baseUrl);

//         String apiUrl = 'http://localhost:8083/users';

//   constructor(private http: HttpClient) {}

//   getAll(): Observable<User[]> {
//     return this.http.get<User[]>(this.apiUrl);
//   }

//   getById(id: number): Observable<User> {
//     return this.http.get<User>(`${this.apiUrl}/`);
//   }


        /*String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("id", 1)
                .build()
                .toUriString();*/
        
        //System.out.println("➡️ Chamando URL: " + url);
      //  return restTemplate.getForObject(url, UserDTO.class,id);
    //}




    // public UserDTO findByEmail(String email) {
    //     String baseUrl = "http://user_service:8080/users/search";
    //     String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
    //             .queryParam("email", email)  // Automatically encodes '@' to '%40'
    //             .toUriString();
        
    //     System.out.println("➡️ Chamando URL: " + url);
    //     return restTemplate.getForObject(url, UserDTO.class,email);
    // }


        //private Object baseUrl;

        public ResponseEntity<UserDTO> findByEmail01(String email) {
        Long id = 1L;
        
            // try (Socket socket = new Socket("localhost", 8080)) {
            // // connection logic
            // } catch (java.net.ConnectException e) {
            // // handle the exception
            // }
         //ip = '192.168.0.6';
        //Socket clientSocket = new Socket(192.168.0.6, 5000);
        String baseUrl = "http://localhost:8083/users/{id}";
        
        // String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
        //         .queryParam("email", email)  // Automatically encodes '@' to '%40'
        //         .toUriString();


        ResponseEntity<UserDTO> entity = new RestTemplate().getForEntity("https://user-service-983c.onrender.com/users/{id}", UserDTO.class,id);
        System.out.println("➡️ Chamando URL: " + baseUrl + " " + entity);
        return entity;
    }




    public ResponseEntity<UserDTO> findByEmail(String email) {
        String baseUrl = "http://user-service:8083/users/search";  // Use service name and internal port

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("email", email)  // Automatically encodes '@' to '%40'
                .toUriString();
        System.out.println("➡️ Chamando URL: " + url);
        ResponseEntity<UserDTO> entity = new RestTemplate().getForEntity(url, UserDTO.class);
    return ResponseEntity.ok(entity.getBody());
}





}

