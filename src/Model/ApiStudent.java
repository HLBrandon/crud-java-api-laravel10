package Model;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiStudent {

    private final String urlApi = "http://127.0.0.1:8000/api/student/";
    private int responseCode;
    
    public List index (Student student) {
        List<Student>datos = new ArrayList<>();
        
        try {
            URL url = new URL(urlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode == 200) { // 200 Codigo HTTP exitoso
                
                StringBuilder stringBuilder = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                
                while (sc.hasNext()) {                    
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                
                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                
                for (int i = 0; i < jsonArray.length(); i++) {
                    student = new Student();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    student.setId(jsonObject.getInt("id"));
                    student.setFirst_name(jsonObject.getString("first_name"));
                    student.setLast_name(jsonObject.getString("last_name"));
                    student.setEmail(jsonObject.getString("email"));
                    student.setAge(jsonObject.getInt("age"));
                    student.setCareer_id(jsonObject.getJSONObject("career").getInt("career_id"));
                    student.setCareer_name(jsonObject.getJSONObject("career").getString("career_name"));
                    datos.add(student);
                }
                
            } else {
                throw new RuntimeException("Ocurrio un error: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        
        return datos;
    }

    public boolean create(Student student) {

        JSONObject post = new JSONObject();
        post.put("first_name", student.getFirst_name());
        post.put("last_name", student.getLast_name());
        post.put("email", student.getEmail());
        post.put("password", student.getPassword());
        post.put("age", student.getAge());
        post.put("career_id", student.getCareer_id());

        try {
            URL url = new URL(urlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = post.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            responseCode = conn.getResponseCode();

            if (responseCode == 201) { // 201 Codigo HTTP para creación exitosa
                StringBuilder stringBuilder = new StringBuilder();
                Scanner sc = new Scanner(conn.getInputStream());
                while (sc.hasNext()) {                    
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONObject data = jsonObject.getJSONObject("data");
               
                student.setId(data.getInt("id"));
                student.setFirst_name(data.getString("first_name"));
                student.setLast_name(data.getString("last_name"));
                student.setEmail(data.getString("email"));
                student.setAge(data.getInt("age"));
                student.setCareer_name(data.getJSONObject("career").getString("career_name"));
                
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean update(Student student) {
        JSONObject post = new JSONObject();
        post.put("first_name", student.getFirst_name());
        post.put("last_name", student.getLast_name());
        post.put("email", student.getEmail());

        if (!"".equals(student.getPassword())) {
            post.put("password", student.getPassword());
        }

        post.put("age", student.getAge());
        post.put("career_id", student.getCareer_id());

        try {
            URL url = new URL(urlApi + student.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = post.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            responseCode = conn.getResponseCode();

            if (responseCode == 200) { // 200 Codigo HTTP para Exito
                StringBuilder stringBuilder = new StringBuilder();
                Scanner sc = new Scanner(conn.getInputStream());
                while (sc.hasNext()) {                    
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONObject data = jsonObject.getJSONObject("data");
                student.setId(data.getInt("id"));
                student.setFirst_name(data.getString("first_name"));
                student.setLast_name(data.getString("last_name"));
                student.setEmail(data.getString("email"));
                student.setAge(data.getInt("age"));
                student.setCareer_name(data.getJSONObject("career").getString("career_name"));
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean show(Student student) {

        try {
            URL url = new URL(urlApi + student.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode != 404) { // 404 codigo HTTP para No Encontrado

                StringBuilder sb = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    sb.append(sc.nextLine());
                }

                sc.close();

                JSONObject jsonObject = new JSONObject(sb.toString());

                student.setId(jsonObject.getInt("id"));
                student.setFirst_name(jsonObject.getString("first_name"));
                student.setLast_name(jsonObject.getString("last_name"));
                student.setEmail(jsonObject.getString("email"));
                student.setAge(jsonObject.getInt("age"));
                student.setCareer_id(jsonObject.getJSONObject("career").getInt("career_id"));
                return true;
                
            } else {
                System.out.println("Response Code: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean delete(Student student) {
        try {
            URL url = new URL(urlApi + student.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean login(User user) {

        JSONObject post = new JSONObject();
        post.put("email", user.getEmail());
        post.put("password", user.getPassword());

        try {
            URL url = new URL("http://127.0.0.1:8000/api/login/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = post.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            responseCode = conn.getResponseCode();

            if (responseCode == 200) { // 201 Codigo HTTP para creación exitosa
                StringBuilder stringBuilder = new StringBuilder();
                Scanner sc = new Scanner(conn.getInputStream());
                while (sc.hasNext()) {                    
                    stringBuilder.append(sc.nextLine());
                }
                sc.close();
                
                JSONObject data = new JSONObject(stringBuilder.toString());
                JSONObject json_user = data.getJSONObject("user");
               
                user.setId(json_user.getInt("id"));
                user.setName(json_user.getString("name"));
                user.setEmail(json_user.getString("email"));
                user.setPassword("");
                user.setAccess_token(data.getString("access_token"));
                user.setType_token(data.getString("type_token"));
                
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean logout(User user) {

        try {
            URL url = new URL("http://127.0.0.1:8000/api/logout/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Authorization", user.getType_token() + " " + user.getAccess_token());
            conn.connect();

            responseCode = conn.getResponseCode();

            if (responseCode != 401) { // 401 codigo HTTP para No Autenticado
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
}
