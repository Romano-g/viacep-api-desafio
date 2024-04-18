import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainCep {
	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner scanner = new Scanner(System.in);
		String json = "";
		int cep = 1;
		List<String> ceps = new ArrayList<>();

		while (cep != 0) {
			System.out.println("Digite um CEP, apenas dígitos, ou digite 0 para sair.");
			cep = scanner.nextInt();
			String cepString = String.valueOf(cep);

			if (cepString.length() != 8) {
				System.out.println("O CEP digitado é inválido.");
				break;
			}
			
			String searchCep = "http://viacep.com.br/ws/" + cep + "/json/";

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(searchCep))
					.build();

			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			json = response.body();
			ceps.add(json);
		}
		System.out.println(json);
		FileWriter fileWriter = new FileWriter("ceps.json");
		fileWriter.write(ceps.toString());
		fileWriter.close();
	}
}
