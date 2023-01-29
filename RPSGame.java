
package javaapplication5;

import java.sql.*;
import java.util.Scanner;

public class RPSGame {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/rps_scores", "root", "");
            System.out.println("Creating statement...");
            Statement stmt = conn.createStatement();
            String sql = "SELECT player_name,score FROM scores ORDER BY score DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            String playerName = "";
            int highSc = 0;
            if (rs.next()) {
                playerName = rs.getString("player_name");
                highSc = rs.getInt("score");
            }
            System.out.println("Current highest score: " + highSc + " by " + playerName);
            Scanner sc = new Scanner(System.in);
            String player_name;
            System.out.println("Enter your name:");
            player_name = sc.nextLine();
            String userCh;
            int userScore = 0;
            while (true) {
                System.out.println("Choose rock (r), paper (p), or scissors (s): ");
                userCh = sc.nextLine();
                switch (userCh) {
                    case "r":
                        System.out.println("You chose rock.");
                        break;
                    case "p":
                        System.out.println("You chose paper.");
                        break;
                    case "s":
                        System.out.println("You chose scissors.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
                int compCh = (int) (Math.random() * 3);
                switch (compCh) {
                    case 0:
                        System.out.println("Computer chose rock.");
                        break;
                    case 1:
                        System.out.println("Computer chose paper.");
                        break;
                    default:
                        System.out.println("Computer chose scissors.");
                        break;
                }
                if (userCh.equals("r") && compCh == 2 ||
                    userCh.equals("p") && compCh == 0 ||
                    userCh.equals("s") && compCh == 1) {
                    System.out.println("You win!");
                    userScore++;
                } else if (userCh.equals("r") && compCh == 0 ||
                    userCh.equals("p") && compCh == 1 ||
                    userCh.equals("s") && compCh == 2) {
                    System.out.println("It's a tie!");
                    } else {
                    System.out.println("You lose!");
                    }
                    System.out.println("Your Score: " + userScore);
                    System.out.println("Play again? (y/n)");
                    if (sc.nextLine().equals("n")) {
                    break;
                    }
                }
        sql = "insert into scores (player_name,score) values ('" + player_name + "'," + userScore + ")";
        stmt.executeUpdate(sql);
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException | ClassNotFoundException se) {
        se.printStackTrace();
    }
    }
}

