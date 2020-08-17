package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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

        if (id == 0){
            model.getFromList().clear();
            pw.print(gson.toJson("Удалены все пользователи." + model.getFromList()));
        }else if (id > 0){
            if (id > model.getFromList().size()){
                pw.print(gson.toJson("Такого пользователя не существует :("));
            }else {
                model.getFromList().remove(id);
                pw.print(gson.toJson("Пользователь с id = " + id + " удалён"));
            }
        }else {
            pw.print(gson.toJson("ID должно быть > 0"));
        }
    }
}
