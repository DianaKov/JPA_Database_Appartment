package jpa_homerwork;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPAhomework");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add apartment");
                    System.out.println("2: view apartment");
                    System.out.println("3: view district");
                    System.out.println("4: view address");
                    System.out.println("5: view area");
                    System.out.println("6: view rooms");
                    System.out.println("7: view price");
                    System.out.println("8: view count");
                    System.out.print("-> ");

                    String choice = sc.nextLine();
                    switch (choice) {
                        case "1":
                            addApartment(sc);
                            break;
                        case "2":
                            viewApartments();
                            break;
                        case "3":
                            viewApartmentsByDistrict(sc);
                            break;
                        case "4":
                            viewApartmentsByAddress(sc);
                            break;
                        case "5":
                            viewApartmentsByArea(sc);
                            break;
                        case "6":
                            viewApartmentsByRooms(sc);
                            break;
                        case "7":
                            viewApartmentsByPrice(sc);
                            break;
                        case "8":
                            viewCount();
                            break;
                        default:
                            return;
                    }
                }
            }finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void viewCount() {
        Query query = em.createQuery("SELECT COUNT(a) FROM Apartment a", Long.class);
        Long count = (Long) query.getSingleResult();
        System.out.println("Number of apartments: " + count);
    }

    private static void viewApartmentsByPrice(Scanner sc) {
        System.out.print("Enter maximum apartment price: ");
        float price = Float.parseFloat(sc.nextLine());

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.price <= :price", Apartment.class);
        query.setParameter("price", price);

        List<Apartment> list = (List<Apartment>) query.getResultList();
        for (Apartment a : list) {
            System.out.println(a);
        }
    }

    private static void viewApartmentsByRooms(Scanner sc) {
        System.out.print("Enter minimum number of apartment rooms: ");
        int rooms = Integer.parseInt(sc.nextLine());

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.rooms >= :rooms", Apartment.class);
        query.setParameter("rooms", rooms);

        List<Apartment> list = (List<Apartment>) query.getResultList();
        for (Apartment a : list) {
            System.out.println(a);
        }
    }

    private static void viewApartmentsByArea(Scanner sc) {
        System.out.print("Enter minimum apartment area: ");
        float area = Float.parseFloat(sc.nextLine());

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.area >= :area", Apartment.class);
        query.setParameter("area", area);

        List<Apartment> list = (List<Apartment>) query.getResultList();
        for (Apartment a : list) {
            System.out.println(a);
        }
    }

    private static void viewApartmentsByAddress(Scanner sc) {
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.address = :address", Apartment.class);
        query.setParameter("address", address);

        List<Apartment> list = (List<Apartment>) query.getResultList();
        for (Apartment a : list) {
            System.out.println(a);
        }
    }

    private static void viewApartmentsByDistrict(Scanner sc) {
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();

        Query query = em.createQuery("SELECT a FROM Apartment a WHERE a.district = :district", Apartment.class);
        query.setParameter("district", district);

        List<Apartment> list = (List<Apartment>) query.getResultList();
        for (Apartment a : list) {
            System.out.println(a);
        }
    }

    private static void viewApartments() {
        Query query = em.createQuery("SELECT a FROM Apartment a", Apartment.class);
        List<Apartment> list = (List<Apartment>) query.getResultList();

        for (Apartment c : list)
            System.out.println(c);
    }


    private static void addApartment(Scanner sc) {
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();
        System.out.print("Enter client area: ");
        float area = Float.parseFloat(sc.nextLine());
        System.out.print("Enter client rooms: ");
        int rooms = Integer.parseInt(sc.nextLine());
        System.out.print("Enter client price: ");
        float price = Float.parseFloat(sc.nextLine());

        em.getTransaction().begin();
        try {
            Apartment c = new Apartment(district, address, area, rooms, price);
            em.persist(c);
            em.getTransaction().commit();

            System.out.println(c.getId());
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }


}
