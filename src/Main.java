import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            FileOutputStream fileout = new FileOutputStream("QUIJOTE_HASH.DAT");
            ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
            MessageDigest md = MessageDigest.getInstance("SHA");
            String datos = "En un lugar de la Mancha de cuyo nombre no quiero acordarme,\n"
                    +"no ha mucho tiempo que vivía un hidalgo de los de lanza en \n"
                    +"astillero, adarga antigua, rocín flaco y galgo corredor. Una olla\n"
                    +"de algo más vaca que carnero, salpicón las más noches, duelos y quebrantos\n"
                    +"los sábados, lentejas los viernes, algún palomino de añadidura\n"
                    +"los domingos, consumían las tres partes de su hacienda. El resto della\n"
                    +"concluían sayo de velarte, calzas de velludo para las fiestas con\n"
                    +"sus pantuflos de lo mismo y los días de entresemana se honraba con su\n"
                    +"cellorí de lo más fino\n"
                    ;
            byte dataBytes[]=datos.getBytes();
            md.update(dataBytes);
            byte resumen[]= md.digest();
            dataOS.writeObject(datos);//aqui ya está cripto
            dataOS.writeObject(resumen);
            dataOS.close();
            fileout.close();
            System.out.println("El texto:"+ datos);
            System.out.println("El hash: "+resumen);
            System.out.println("\nResumen SHA-256 del Quijote creado con exito");
            //ahora vamos a importarlo y comprobarlo como adicional
            try {
                FileInputStream filein= new FileInputStream("QUIJOTE_HASH.DAT");
                ObjectInputStream dat= new ObjectInputStream(filein);
                Object obj = dat.readObject();
                //PRIMERA LECTURA; SE OBTIENE EL STRING
                String quijote = (String)obj;
                System.out.println("Datos importados: "+ quijote);
                //SEGUDNA LECTURA, SE OBTIENE EL RESUMEN
                obj=dat.readObject();
                byte resumenOriginal[]=(byte[])obj;

                MessageDigest mess = MessageDigest.getInstance("SHA");
                mess.update(quijote.getBytes());
                byte resumenActual[]=mess.digest();
                //SE COMPRUEBAN SI LOS 2 RESUMENES SON IGUALES
                if(MessageDigest.isEqual(resumenActual, resumenOriginal)) {
                    System.out.println("\n DATOS IMPORTADOS VALIDOS");
                }else {
                    System.out.println("\n DATOS IMPORTADOS NO VALIDOS");
                }
                dataOS.close();
                fileout.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }catch(IOException e) {
            e.printStackTrace();
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
//el git sería
//Para poder subir al repositorio, siendo el mio con usuario gullon1995 y voy a ponerle de nombre examenHash
//Lo primero que tenemos que hacer tras el user sería realizar un init
//git init
//añadimos el ejercicio en la carpeta examen
//git add examenHash
//preparamos el envio con el commit
//git commit -m "first commit"
//añadimos origen
//git remote add origin https://github.com/gullon1995/examenprocesos.git
//finalmente lo enviamos con el push
//git push -u origin master