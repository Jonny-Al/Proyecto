/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import ModeloVO.CUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

/**
 *
 * @author ALEJANDRO
 */
public class CEmail {

    Properties propiedad = new Properties();

    public int enviarEmail(CUsuario us, String accion) throws NamingException {

        Context context = (Context) new InitialContext().lookup("java:comp/env");
        int codigo = 0;

        propiedad.put("mail.smtp.host", "smtp.gmail.com");
        propiedad.put("mail.smtp.starttls.enable", "true");
        propiedad.put("mail.smtp.port", "587");
        propiedad.put("mail.smtp.auth", "true");

        try {

            final String emailenvia = (String) context.lookup("correo");
            final String password = (String) context.lookup("passcorreo");

            Session sesion = Session.getInstance(propiedad, null);
            String htmlCode;

            MimeMessage mail = new MimeMessage(sesion);

            try {

                // Email que envia el mensaje
                mail.setFrom(new InternetAddress(emailenvia));
                // Linea donde se indica a que correo electronico se envia el mensaje

                if (accion.equalsIgnoreCase("Recovery")) {
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(us.getCorreoalterno()));
                } else {
                    mail.addRecipient(Message.RecipientType.TO, new InternetAddress(us.getCorreo()));
                }

                Random rnd = new Random();
                codigo = rnd.nextInt(2153482) * 6;

                switch (accion) {
                    case "Recovery": // Case para recuperacion de cuenta

                        mail.setSubject("CONFIRMACIÓN DE CUENTA.");
                        htmlCode = " <div style=\"background-color: rgb(88, 37, 146); margin-top: auto; width: auto; border-radius: 15px; max-height: 220px; min-height: 220px; text-align: center;\">\n"
                                + "        <img style=\"background-color: rgb(88, 37, 146); border-radius: 15px; margin: 10px;\" src=\"https://1.bp.blogspot.com/-JMgMLMJ-HCE/XqSGjTrQ5qI/AAAAAAAABe0/4SaVMPObUdY5vkki52r4em39uVr4egVDwCLcBGAsYHQ/s1600/WC10.png\">\n"
                                + "        <br>\n"
                                // + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px; \">Hola " + us.getNombres() + "</h4>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px; \"> Tu código de confirmación es: " + codigo + "</h4>\n"
                                + "    </div>";
                        mail.setContent(htmlCode, "text/html");
                        // mail.setText("Tu codigo de confirmacion es: " + codigo);

                        break;

                    case "Activar cuenta":

                        // Se envia a correo electronico de un usuario para que realice activacion de la cuenta
                        mail.setSubject("ACTIVACIÓN DE CUENTA EN WOOCLIC.");
                        htmlCode = "<style> a:link { text-decoration: none; } </style> <div style=\"background-color: rgb(88, 37, 146); margin-top: auto; width: auto; border-radius: 15px; max-height: 220px; min-height: 310px; text-align: center;\">\n"
                                + "        <img style=\"background-color: rgb(88, 37, 146); border-radius: 15px; margin: 10px;\" src=\"https://1.bp.blogspot.com/-JMgMLMJ-HCE/XqSGjTrQ5qI/AAAAAAAABe0/4SaVMPObUdY5vkki52r4em39uVr4egVDwCLcBGAsYHQ/s1600/WC10.png\">\n"
                                + "        <br>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Hola " + us.getNombres() + " </h4>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Activa tu cuenta en el siguiente botón</h4>\n"
                                + "        <a href=\'http://localhost:8080/WooClic/validate?accion=activateaccount&n=" + us.getNombres().replace(" ", "") + "&l=" + us.getApellidos().replace(" ", "") + "&t=" + us.getTelefono() + "&e=" + us.getCorreo() + "&a=" + us.getArea() + "&r=" + us.getRol() + "'>"
                                + "             <div style=\"border-radius: 20px; background-color: rgb(58, 0, 100);  margin-left: 30px; margin-right: 30px; height: 50px;\">"
                                + "                 <div style=\" font-size: 20px; text-align: center; color: rgb(255, 255, 255); text-decoration: none; font-family: Arial, Helvetica, sans-serif; padding-top: 15px;\">"
                                + "                    ACTIVAR"
                                + "                </div>\n"
                                + "            </div>\n"
                                + "        </a>\n"
                                + "    </div>";

                        mail.setContent(htmlCode, "text/html");
                        break;

                    case "AddUser":

                        mail.setSubject("INGRESO A WOOCLIC.");

                        htmlCode = "<style> a:link { text-decoration: none; } </style> <div style=\"background-color: rgb(88, 37, 146); margin-top: auto; width: auto; border-radius: 15px; max-height: 220px; min-height: 310px; text-align: center;\">\n"
                                + "        <img style=\"background-color: rgb(88, 37, 146); border-radius: 15px; margin: 10px;\" src=\"https://1.bp.blogspot.com/-JMgMLMJ-HCE/XqSGjTrQ5qI/AAAAAAAABe0/4SaVMPObUdY5vkki52r4em39uVr4egVDwCLcBGAsYHQ/s1600/WC10.png\">\n"
                                + "        <br>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Bienvenido, ahora eres parte de nuestro equipo, disfruta con tu grupo una gran experiencia de trabajo.</h4>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Ingresa aqui con tu siguiente clave " + us.getClave() + " y recuerda cambiarla al ingresar.</h4>\n"
                                + "        <a href=\'http://localhost:8080/WooClic/index.jsp'>"
                                + "             <div style=\"border-radius: 20px; background-color: rgb(58, 0, 100);  margin-left: 30px; margin-right: 30px; height: 50px;\">"
                                + "                 <div style=\" font-size: 20px; text-decoration: none; text-align: center; color: rgb(255, 255, 255); font-family: Arial, Helvetica, sans-serif; padding-top: 15px;\">"
                                + "                    INGRESAR"
                                + "                </div>\n"
                                + "            </div>\n"
                                + "        </a>\n"
                                + "    </div>";

                        mail.setContent(htmlCode, "text/html");

                        break;

                    case "Claveactualizada":

                        Date fechaactual = new Date(System.currentTimeMillis());
                        SimpleDateFormat dateformat = new SimpleDateFormat("dd / MM / yyyy");

                        mail.setSubject("CONTRASEÑA ACTUALIZADA.");
                        htmlCode = "<style> a:link { text-decoration: none; } </style> <div style=\"background-color: rgb(88, 37, 146); margin-top: auto; width: auto; border-radius: 15px; max-height: 220px; min-height: 310px; text-align: center;\">\n"
                                + "        <img style=\"background-color: rgb(88, 37, 146); border-radius: 15px; margin: 10px;\" src=\"https://1.bp.blogspot.com/-JMgMLMJ-HCE/XqSGjTrQ5qI/AAAAAAAABe0/4SaVMPObUdY5vkki52r4em39uVr4egVDwCLcBGAsYHQ/s1600/WC10.png\">\n"
                                + "        <br>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Hola " + us.getNombres() + " tu clave fue actualizada el " + dateformat.format(fechaactual) + ", si no fuiste tu ingresa al siguiente botón.</h4>\n"
                                + "        <a href=\'http://localhost:8080/WooClic/confirmemail.jsp'>"
                                + "             <div style=\"border-radius: 20px; background-color: rgb(58, 0, 100);  margin-left: 30px; margin-right: 30px; height: 50px;\">"
                                + "                 <div style=\" font-size: 20px; text-decoration: none; text-align: center; color: rgb(255, 255, 255); font-family: Arial, Helvetica, sans-serif; padding-top: 15px;\">"
                                + "                    COMPROBAR"
                                + "                </div>\n"
                                + "            </div>\n"
                                + "        </a>\n"
                                + "    </div>";

                        mail.setContent(htmlCode, "text/html");

                        break;

                    case "Validaremail":

                        mail.setSubject("CONFIRMACION DE CORREO ALTERNATIVO");
                        htmlCode = " <div style=\"background-color: rgb(88, 37, 146); margin-top: auto; width: auto; border-radius: 15px; max-height: 200px; min-height: 210px; text-align: center;\">\n"
                                + "        <img style=\"background-color: rgb(88, 37, 146); border-radius: 15px; margin: 10px;\" src=\"https://1.bp.blogspot.com/-JMgMLMJ-HCE/XqSGjTrQ5qI/AAAAAAAABe0/4SaVMPObUdY5vkki52r4em39uVr4egVDwCLcBGAsYHQ/s1600/WC10.png\">\n"
                                + "        <br>\n"
                                + "        <h4 style=\"color: white; font-family: Arial, Helvetica, sans-serif; margin: 10px; margin-top: 10px;\">Hola " + us.getNombres() + " ,tu código de confirmación es " + codigo + ".</h4>\n"
                                + "     </div>";

                        mail.setContent(htmlCode, "text/html");

                        break;
                    default:
                        throw new AssertionError();
                }

                try (Transport trs = sesion.getTransport("smtp")) {
                    trs.connect(emailenvia, password);
                    trs.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                }

            } catch (MessagingException e) {
                System.out.println("Error en el envio del correo " + e);
                Logger.getLogger(CEmail.class.getName()).log(Level.SEVERE, null, e);
                codigo = 0;
            }

        } catch (NamingException e) {
            System.out.println("Error en el envio de correo electronico" + e);
        }
        return codigo;
    }
}
