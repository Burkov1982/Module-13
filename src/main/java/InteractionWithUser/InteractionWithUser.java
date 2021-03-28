package InteractionWithUser;

import DataStorage.*;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InteractionWithUser {

    public String add(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Utils.URI + Utils.ADD_USER_PART))
                .POST(HttpRequest.BodyPublishers.ofString(Utils.gson.toJson(user)))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String update(int userId, User updatedUser) throws IOException, InterruptedException {
        String requestBody = Utils.gson.toJson(updatedUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", Utils.URI, Utils.ADD_USER_PART, userId)))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public int delete(User user) throws IOException, InterruptedException {
        String requestBody = Utils.gson.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", Utils.URI, Utils.ADD_USER_PART, user.getId())))
                .header("Content-type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public String getUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Utils.URI + Utils.ADD_USER_PART+"/"))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getUserID(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", Utils.URI, Utils.ADD_USER_PART, id)))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getUserByUsername(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%s", Utils.URI, Utils.ADD_USER_PART, "?username="+username)))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getLastPostByUserID(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d/posts", Utils.URI, Utils.ADD_USER_PART, id)))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        ArrayList<Post> posts = Utils.gson.fromJson(response.body(), new TypeToken<ArrayList<Post>>(){}.getType());
        Post post = Collections.max(posts, Comparator.comparingInt(Post::getId));
        return new GsonBuilder().setPrettyPrinting().create().toJson(post);
    }

    public String getCommentsByPIDAndWInFile(int id) throws IOException, InterruptedException {
        Post post = Utils.gson.fromJson(getLastPostByUserID(id), new TypeToken<Post>(){}.getType());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d/%s", Utils.URI, "posts", post.getId(), "comments")))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        List<Comment> commentsList = Utils.gson.fromJson(response.body(), new TypeToken<List<Comment>>(){}.getType());
        String fileName = String.format("user-%d-post-%d-comments.json", id, post.getId());
        File file = new File("src\\main\\resources\\"+fileName);
        if (!file.exists()){
            file.createNewFile();
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(new GsonBuilder().setPrettyPrinting().create().toJson(commentsList));
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "Comments in file";
    }

    public String getTodosByObtainedUser(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d/posts", Utils.URI, Utils.ADD_USER_PART, id)))
                .GET()
                .build();
        HttpResponse<String> response = Utils.client.send(request, HttpResponse.BodyHandlers.ofString());
        ArrayList<Todo> todos = Utils.gson.fromJson(response.body(), new TypeToken<ArrayList<Todo>>(){}.getType());
        ArrayList<Todo> result = new ArrayList<>();
        for (Todo todo:todos) {
            if (todo.isCompleted() == false){
                result.add(todo);
            }
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(result);
    }
}
