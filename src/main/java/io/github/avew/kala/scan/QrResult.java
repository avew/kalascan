package io.github.avew.kala.scan;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrResult {

	private boolean error;
	private String data;
	private String message;

	@Override
	public String toString() {
		return "{" +
				"error=" + error +
				", data='" + data + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
