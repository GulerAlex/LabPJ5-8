package Laborator8;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

import static java.lang.System.exit;

public class MainApp {
    public static void adaugare(Connection connection, String nume, int varsta) {
        String sql="insert into persoane(nume,varsta) values (?,?)";
        try(PreparedStatement ps=connection.prepareStatement(sql)) {
            ps.setString(1, nume);
            ps.setInt(2, varsta);
            int nr_randuri=ps.executeUpdate();
            System.out.println("\nNumar randuri afectate de adaugare="+nr_randuri);
        } catch (SQLException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }
    public static void adaugaExcursie(Scanner sc,Connection connection,int id_c){
        String sql1="select * from persoane where id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql1)){
            preparedStatement.setInt(1, id_c);
            try(ResultSet rs=preparedStatement.executeQuery()){
                if(rs.next()&&rs.getInt(1)>0){
                    System.out.println("Adauga excursie: ");
                    System.out.print("Destinatia: ");
                    String destinatia=sc.nextLine();
                    int anul;
                    int anul_nasterii= LocalDate.now().getYear()-rs.getInt("varsta");
                    while(true){
                        try{
                            System.out.print("Anul: ");
                            anul=Integer.parseInt(sc.nextLine());
                            valideaza_AnExcursie(anul,anul_nasterii);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Anul trebuie sa fie un numar!");
                        } catch (ExceptieAnExcursie e) {
                            System.out.println("Eroare: "+e.getMessage());
                        }
                    }

                    String sql2="insert into excursii(id_persoana,destinatia,anul) values (?,?,?)";
                    try(PreparedStatement preparedStatement2=connection.prepareStatement(sql2)) {
                        preparedStatement2.setInt(1, id_c);
                        preparedStatement2.setString(2, destinatia);
                        preparedStatement2.setInt(3, anul);
                        int nr_randuri=preparedStatement2.executeUpdate();
                        System.out.println("Excursie adaugata");
                    }
                }
                else{
                    System.out.println("Persoana nu exista in tabela persoane!");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void afisarePersoaneExcursii(Connection connection){
        String sql="select * from persoane";
        try(PreparedStatement ps=connection.prepareStatement(sql);ResultSet rsP=ps.executeQuery()){
            while(rsP.next()){
                int id=rsP.getInt("id");
                String nume=rsP.getString("nume");
                int varsta=rsP.getInt("varsta");
                System.out.println("Persoana: "+nume +" Varsta: "+varsta);
                String sql1="select * from excursii where id_persoana=?";
                try(PreparedStatement preparedStatement=connection.prepareStatement(sql1)) {
                    preparedStatement.setInt(1, id);
                    try(ResultSet rs=preparedStatement.executeQuery()){
                        while(rs.next()){
                            int id_persoana=rs.getInt("id_persoana");
                            int id_excursie=rs.getInt("id_excursie");
                            String destinatia=rs.getString("destinatia");
                            int anul=rs.getInt("anul");
                            System.out.println("    Excursia: "+id_excursie+" Destinatia: "+destinatia+" Anul: "+anul);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void AfisareExcursii(Connection connection,String nume_c){
        System.out.println("Excursiile la care a mers "+nume_c+" sunt: ");
        String sql="select id from persoane where nume=?";
        try(PreparedStatement ps=connection.prepareStatement(sql)){
            ps.setString(1, nume_c);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()&&rs.getInt(1)>0){
                    String sql2="select * from excursii where id_persoana=?";
                    try(PreparedStatement preparedStatement2=connection.prepareStatement(sql2)) {
                        preparedStatement2.setInt(1, rs.getInt("id"));
                        try(ResultSet rs2=preparedStatement2.executeQuery()){
                            while(rs2.next()){
                                String destinatia=rs2.getString("destinatia");
                                int anul=rs2.getInt("anul");
                                System.out.println(destinatia + " in anul: "+anul);
                            }
                        }
                    }
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void afisare_excursii(Connection connection,String nume_ex){
        String sql="select * from excursii where destinatia=?";
        System.out.println("Persoanele care au mers in "+nume_ex+" sunt: ");
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setString(1, nume_ex);
            try(ResultSet rs=preparedStatement.executeQuery()){
                if(rs.next()&&rs.getInt(1)>0){
                    String sql2="select * from persoane where id=?";
                    try(PreparedStatement preparedStatement2=connection.prepareStatement(sql2)) {
                        preparedStatement2.setInt(1, rs.getInt("id_persoana"));
                        try(ResultSet rs2=preparedStatement2.executeQuery()){
                            while(rs2.next()){
                                String nume=rs2.getString("nume");
                                System.out.println(nume);
                            }
                        }
                    }
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void afisare_an(Connection connection,int an){
        String sql="select * from excursii where anul=?";
        System.out.println("Persoanele care au mers in excursii in anul "+an+" sunt: ");
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, an);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    String sql2 = "select * from persoane where id=?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(sql2)) {
                        preparedStatement2.setInt(1, rs.getInt("id_persoana"));
                        try (ResultSet rs2 = preparedStatement2.executeQuery()) {
                            while (rs2.next()) {
                                String nume = rs2.getString("nume");
                                System.out.println(nume);
                            }
                        }
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void stergere_exc(Connection connection,int id){
        String sql="delete from excursii where id_excursie=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Stergere efectuata cu succes!");
    }
    public static void stergere_pers(Connection connection,int id){
        String sql="select * from persoane where id=?";
        try(PreparedStatement preparedStatement=connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String sql2="delete from excursii where id_persoana=?";
                try(PreparedStatement preparedStatement2=connection.prepareStatement(sql2)) {
                    preparedStatement2.setInt(1, resultSet.getInt("id"));
                    preparedStatement2.executeUpdate();
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        String sql3="delete from persoane where id=?";
        try(PreparedStatement ps=connection.prepareStatement(sql3)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Stergere efectuata cu succes!");
    }
    static void valideaza_varsta(int varsta) throws ExceptieVarsta {
        if(varsta < 0 || varsta > 120)
            throw new ExceptieVarsta("Varsta invalida! Introduceti intre 0 si 120! ");
    }
    static void valideaza_AnExcursie(int an,int an_nastere) throws ExceptieAnExcursie {
        if(an<an_nastere){
            throw new ExceptieAnExcursie("Anul trebuie sa fie dupa anul nasterii!");
        }
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/pj";
        Connection connection= DriverManager.getConnection(url, "root", "root");
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("\nMeniul");
            System.out.println("0. Ieșire");
            System.out.println("1. Adaugă persoană");
            System.out.println("2. Adaugă excursie");
            System.out.println("3. Vizualizează persoane");
            System.out.println("4. Vizualizează excursii al unei persoane citite");
            System.out.println("5. Vizualizează persoane care au fost intr-o destinatie");
            System.out.println("6. Afișarea persoanelor care au făcut excursii într-un an introdus ");
            System.out.println("7. Stergerea unei excursii");
            System.out.println("8. Stergerea unei persoane cu tot cu excursiile ei");
            System.out.print("Introduceti o optiune: ");
            String opt=sc.nextLine();
            int id;
            switch(opt){
                case "0":
                    exit(0);
                case "1":
                    System.out.print("Numele: ");
                    String nume=sc.nextLine();
                    int varsta=0;
                    while(true){
                        try{
                            System.out.print("Varsta: ");
                            varsta=Integer.parseInt(sc.nextLine());
                            valideaza_varsta(varsta);
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Introduceti un numar pentru varsta!");;
                        } catch (ExceptieVarsta e) {
                            System.out.println("Eroare: "+e.getMessage());
                        }
                    }
                    adaugare(connection,nume,varsta);
                    break;
                case "2":
                    System.out.print("Introduceti IDul persoanei dorite: ");
                    int id_c=sc.nextInt();
                    sc.nextLine();
                    adaugaExcursie(sc,connection,id_c);
                    break;
                case "3":
                    afisarePersoaneExcursii(connection);
                    break;
                case "4":
                    System.out.print("Numele persoanei cautate: ");
                    String nume_c=sc.nextLine();
                    AfisareExcursii(connection,nume_c);
                    break;
                case "5":
                    System.out.print("Numele excursiei dorite: ");
                    String excursie=sc.nextLine();
                    afisare_excursii(connection,excursie);
                    break;
                case "6":
                    System.out.print("Anul dorit: ");
                    int anul=sc.nextInt();
                    sc.nextLine();
                    afisare_an(connection,anul);
                    break;
                case "7":
                    System.out.print("IDul excursiei sterse: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    stergere_exc(connection,id);
                    break;
                case "8":
                    System.out.print("IDul persoanei sterse: ");
                    id=sc.nextInt();
                    sc.nextLine();
                    stergere_pers(connection,id);
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }while(true);

    }

}

