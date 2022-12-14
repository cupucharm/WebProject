package Board;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardFileVO implements Serializable {
	
	private static final long serialVersionUID = 8038712129022363663L;
	private int fid;
	private int fbno;
	private String forg_name;
	private String freal_name;
	private String fcontent_type;
	private String fdate;
	private long flength;
}
