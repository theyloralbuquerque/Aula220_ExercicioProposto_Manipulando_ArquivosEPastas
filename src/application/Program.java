package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Product> list = new ArrayList<>(); // Cria��o de uma lista onde ser�o armazenados os produtos.

        System.out.println("Digite o caminho do arquivo: ");
        String origemDoArquivoStr = sc.nextLine(); // O caminho que o usu�rio digitar ser� armazenado na vari�vel origemDoArquivoStr.

        File origemDoArquivo = new File(origemDoArquivoStr); // O objeto origemDoArquivo do tipo File � instanciado com o valor de origemDoArquivoStr.
        String caminhoDaPastaStr = origemDoArquivo.getParent(); // A vari�vel caminhoDaPastaStr recebe o caminho do objeto origemDoArquivo.

        // A vari�vel sucesso recebe a cria��o de uma nova pasta, tendo caminhoDaPastaStr como endere�o e "out" como nome.
        boolean sucesso = new File(caminhoDaPastaStr + "\\out").mkdir();

        // A vari�vel destinoDoArquivoStr recebe o valor de caminhoDaPastaStr + "\\out\\sumary.csv", ou seja...
        // destinoDoArquivoStr = c:\temp\out\sumary.csv.
        String destinoDoArquivoStr = caminhoDaPastaStr + "\\out\\sumary.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(origemDoArquivo))){
            String itemCsv = br.readLine(); // .readLine() l� uma linha do arquivo.
            while (itemCsv != null) { // Enquanto a linha do arquivo for diferente de null.
                String[] campos = itemCsv.split(","); // .split() ir� dividir a linha onde tiver v�rgula.
                String nome = campos[0]; // Na posi��o 0 do vetor est� o nome do produto.
                double preco = Double.parseDouble(campos[1]); // Na posi��o 1 do vetor est� o pre�o do produto.
                int quantidade = Integer.parseInt(campos[2]); // Na posi��o 2 do vetor est� a quantidade do produto.

                list.add(new Product(nome, preco, quantidade)); // Adiciona os valores na lista instanciando a classe product.

                itemCsv = br.readLine(); // .readLine() ir� ler mais uma linha do arquivo.
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(destinoDoArquivoStr))) {

                for (Product item : list) { // Para cada objeto item do tipo Product na Lista list:
                    bw.write(item.getNome() + ", " + String.format("%.2f", item.total()));
                    bw.newLine(); // .newLine() passa para uma nova linha.
                }

                System.out.println(destinoDoArquivoStr + " criado!");
            }
            catch (IOException e) {
                System.out.println("Erro ao gravar arquivo: " + e.getMessage());
            }
        }
        catch (IOException e) {
            System.out.println("Erro ao gravar arquivo: " + e.getMessage());
        }
    }
}