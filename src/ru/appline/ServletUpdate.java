package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/put")
public class ServletUpdate extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                jb.append(line);
            }
        }catch (Exception e){
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        PrintWriter pw = response.getWriter();

        int id = jobj.get("id").getAsInt();
        String newName = jobj.get("name").getAsString();
        String newSurname = jobj.get("surname").getAsString();
        int newSalary = jobj.get("salary").getAsInt();

        if (id > 0){
            if (id > model.getFromList().size()){
                pw.print(gson.toJson("Такого пользователя не существует :("));
            }else {
                model.getFromList().replace(id, new User(newName,newSurname, newSalary));
                pw.print(gson.toJson(model.getFromList().get(id)));
            }
        }else {
            pw.print(gson.toJson("ID должен быть >= 0"));
        }
    }
}
