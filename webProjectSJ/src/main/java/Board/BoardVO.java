package Board;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO implements Serializable {
	private static final long serialVersionUID = 5783936267253111186L;
	
	private int bno;
	private String btitle;
	private String bwriter;
	private String bdate;
	private int bhit;
	private String bcontents;
	private String bcategory;

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardVO other = (BoardVO) obj;
		return bno == other.bno;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bno);
	}
}
