package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/calc")
public class ServletCalculator extends HttpServlet {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        int result = 0;
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }

        JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);
        PrintWriter pw = response.getWriter();

        int a = jobj.get("a").getAsInt();
        int b = jobj.get("b").getAsInt();
        String math = jobj.get("math").getAsString();

        if (math.equals("+")){
            result = a + b;
            pw.print(gson.toJson("Result:" + result));
        }else if (math.equals("-")){
            result = a - b;
            pw.print(gson.toJson("Result:" + result));
        }else if (math.equals("*")){
            result = a * b;
            pw.print(gson.toJson("Result:" + result));
        }else if (math.equals("/")){
            if (b != 0){
                result = a / b;
                pw.print(gson.toJson("Result:" + result));
            }else {
                pw.print(gson.toJson("Неполучается разделить :("));
            }
        }else {
            pw.print(gson.toJson("Неверная операция! Калькулятор умеет: +, -, *, /"));
        }
    }
}
