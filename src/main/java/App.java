import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
        EntityManager em = emf.createEntityManager();

        preInsert(em);
        
        int option;
        
        do {
            option = menu(scanner);

            if(option == 0){
                System.out.println("Saindo do Sistema...Aguarde...");
            }else if(option == 1){
                insert(em, scanner);
            } else if (option == 2 || option == 3) {
                findRemove(em, scanner, option);
            }
        }while(option!=0);

        System.out.println("Obrigado e volte sempre.");
        
        em.close();
        emf.close();
    }

    public static void preInsert(EntityManager em){
        Produto p1 = new Produto(null, "Contra Filé", "Açougue");
        Produto p2 = new Produto(null, "Veja Multiuso", "Limpeza");
        Produto p3 = new Produto(null, "Tomate", "Hortifruti");

        Cliente c1 = new Cliente(null, "William", "Pouso Alegre", "Masculino", "12/06/1987");
        Cliente c2 = new Cliente(null, "Matheus", "Bom Repouso", "Masculino", "02/02/1973");
        Cliente c3 = new Cliente(null, "Pedro", "Turvolândia", "Masculino", "03/03/1980");

        Funcionario f1 = new Funcionario(null, "Maria", "Caixa");
        Funcionario f2 = new Funcionario(null, "José", "Repositor");
        Funcionario f3 = new Funcionario(null, "Sebastião", "Gerente de Vendas");

        em.getTransaction().begin();

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        em.persist(c1);
        em.persist(c2);
        em.persist(c3);

        em.persist(f1);
        em.persist(f2);
        em.persist(f3);

        em.getTransaction().commit();
    }

    public static void insert(EntityManager em, Scanner scanner){
        System.out.println("Digite o que quer inserir no Banco de Dados:\n");
        System.out.println("1.Produto\n2.Cliente\n3.Funcionário\n");
        int option = scanner.nextInt();scanner.nextLine();

        if(option == 1){
            System.out.println("Digite o nome do Produto: ");
            String nomeProduto = scanner.nextLine();
            System.out.println("Digite o setor do Produto: ");
            String setorProduto = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Produto p WHERE p.nome = :nome");
            query.setParameter("nome", nomeProduto);

            List<Produto> produtosEncontrados = query.getResultList();

            if (produtosEncontrados.isEmpty()) {
                System.out.println("Inserindo o Produto...");
                Produto p = new Produto(null, nomeProduto, setorProduto);
                persist(em, p);

            } else {
                for (Produto produto : produtosEncontrados) {
                    System.out.println("O Produto ==>> ID: " + produto.getId() + ", Nome: " + produto.getNome() +
                            ", já se encontra em nosso banco de dados!");
                    System.out.println("Gostaria de atualizá-lo com as novas informações?\n");
                    System.out.println("1.Sim\n2.Não");

                    int subOption = scanner.nextInt();scanner.nextLine();

                    if(subOption == 2){
                        System.out.println("OK! Voltando ao menu anterior.");
                    }else if(subOption == 1){
                        System.out.println("Ok! Atualizando o Produto...");
                        Produto p = new Produto(produto.getId(), nomeProduto, setorProduto);
                        merge(em, p);
                    }else{
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            }
        }else if(option == 2){
            System.out.println("Digite o nome do Cliente: ");
            String nomeCliente = scanner.nextLine();
            System.out.println("Digite o endereço do Cliente : ");
            String endCliente = scanner.nextLine();
            System.out.println("Digite o gênero do Cliente : ");
            String generoCliente = scanner.nextLine();
            System.out.println("Digite a data de nascimento do Cliente : ");
            String dataNascCliente = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Cliente p WHERE p.nome = :nome");
            query.setParameter("nome", nomeCliente);

            List<Cliente> clientesEncontrados = query.getResultList();

            if (clientesEncontrados.isEmpty()) {
                System.out.println("Inserindo o Cliente...");
                Cliente c = new Cliente(null, nomeCliente, endCliente , generoCliente, dataNascCliente);
                persist(em, c);
            } else {
                for (Cliente cliente : clientesEncontrados) {
                    System.out.println("O Cliente ==>> ID: " + cliente.getCpf() + ", Nome: " + cliente.getNome() +
                            ", já se encontra em nosso banco de dados!");
                    System.out.println("Gostaria de atualizá-lo com as novas informações?\n");
                    System.out.println("1.Sim\n2.Não");

                    int subOption = scanner.nextInt();scanner.nextLine();

                    if(subOption == 2){
                        System.out.println("OK! Voltando ao menu anterior.");
                    }else if(subOption == 1){
                        System.out.println("Ok! Atualizando o Cliente...");
                        Cliente c = new Cliente(cliente.getCpf(), nomeCliente, endCliente, generoCliente, dataNascCliente);
                        merge(em, c);
                    }else{
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            }
        } else if (option == 3) {
            System.out.println("Digite o nome do Funcionário: ");
            String nomeFuncionario = scanner.nextLine();
            System.out.println("Digite a função do Funcionário: ");
            String funcaoFuncionario = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Funcionario p WHERE p.nome = :nome");
            query.setParameter("nome", nomeFuncionario);

            List<Funcionario> funcionariosEncontrados = query.getResultList();

            if (funcionariosEncontrados.isEmpty()) {
                System.out.println("Inserindo o Funcionário...");
                Funcionario f = new Funcionario(null, nomeFuncionario, funcaoFuncionario);
                persist(em, f);
            } else {
                for (Funcionario funcionario : funcionariosEncontrados) {
                    System.out.println("O Funcionário ==>> ID: " + funcionario.getId() + ", Nome: " + funcionario.getNome() +
                            funcionario.getFuncao() + ", já se encontra no nosso banco de dados!");
                    System.out.println("Gostaria de atualizá-lo com as novas informações?\n");
                    System.out.println("1.Sim\n2.Não");

                    int subOption = scanner.nextInt();scanner.nextLine();

                    if(subOption == 2){
                        System.out.println("OK! Voltando ao menu anterior.");
                    }else if(subOption == 1){
                        System.out.println("Ok! Atualizando o Cliente...");
                        Funcionario f = new Funcionario(funcionario.getId(), nomeFuncionario, funcaoFuncionario);
                        merge(em, f);
                    }else{
                        System.out.println("Opção inválida! Tente novamente.");
                    }
                }
            }

        }else{
            System.out.println("Dados não encontrados!");
        }
    }
    public static void persist(EntityManager em, Object p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public static void merge(EntityManager em, Object p){
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    public static void findRemove(EntityManager em, Scanner scanner, int remove){
        if(remove == 3){
            System.out.println("Digite o que quer remover:\n");
        }else{
            System.out.println("Digite o que quer prcourar:\n");
        }

        System.out.println("1.Produto\n2.Cliente\n3.Funcionário\n");
        int option = scanner.nextInt();scanner.nextLine();

        if(option == 1){
            System.out.println("Digite o nome do produto: ");
            String nomeProduto = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Produto p WHERE p.nome = :nome");
            query.setParameter("nome", nomeProduto);

            List<Produto> produtosEncontrados = query.getResultList();

            if (produtosEncontrados.isEmpty()) {
                System.out.println("Nenhum produto encontrado.");
            } else {
                for (Produto produto : produtosEncontrados) {
                    if(remove == 3){
                        em.getTransaction().begin();
                        Produto remP = em.find(Produto.class, produto.getId());
                        em.remove(remP);
                        em.getTransaction().commit();
                        System.out.print("Removendo o Produto ==>> ");
                    }
                    System.out.println("ID: " + produto.getId() + ", Nome: " + produto.getNome());
                }
            }
        }else if(option == 2){
            System.out.println("Digite o nome do Cliente: ");
            String nomeCliente = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Cliente p WHERE p.nome = :nome");
            query.setParameter("nome", nomeCliente);

            List<Cliente> clientesEncontrados = query.getResultList();

            if (clientesEncontrados.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                for (Cliente cliente : clientesEncontrados) {
                    if(remove == 3){
                        em.getTransaction().begin();
                        Cliente remC = em.find(Cliente.class, cliente.getCpf());
                        em.remove(remC);
                        em.getTransaction().commit();
                        System.out.print("Removendo o Cliente ==>> ");
                    }
                    System.out.println("ID: " + cliente.getCpf() + ", Nome: " + cliente.getNome());
                }
            }
        } else if (option == 3) {
            System.out.println("Digite o nome do Funcionário: ");
            String nomeFuncionario = scanner.nextLine();

            Query query = em.createQuery("SELECT p FROM Funcionario p WHERE p.nome = :nome");
            query.setParameter("nome", nomeFuncionario);

            List<Funcionario> funcionariosEncontrados = query.getResultList();

            if (funcionariosEncontrados.isEmpty()) {
                System.out.println("Nenhum funcionário encontrado.");
            } else {
                for (Funcionario funcionario : funcionariosEncontrados) {
                    if(remove == 3){
                        em.getTransaction().begin();
                        Funcionario remF = em.find(Funcionario.class, funcionario.getId());
                        em.remove(remF);
                        em.getTransaction().commit();
                        System.out.print("Removendo o Funcionário ==>> ");
                    }
                    System.out.println("ID: " + funcionario.getId() + ", Nome: " + funcionario.getNome());
                }
            }
        }else{
            System.out.println("Dados não encontrados!");
        }
    }
    public static int menu(Scanner scanner){
        System.out.println("## Cadastro de Dados ##\n");
        System.out.println("Digite 1 para Inserir ou Atualizar");
        System.out.println("Digite 2 para Buscar");
        System.out.println("Digite 3 para Remover\n");
        System.out.println("Digite 0 para Sair");

        int option = scanner.nextInt();scanner.nextLine();
        return option;
    }





}



























