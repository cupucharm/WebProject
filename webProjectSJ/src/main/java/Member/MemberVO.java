package Member;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberVO implements Serializable {
	private static final long serialVersionUID = 5783936267253111186L;
	
	private String uid;
	private String pwd;
	private String name;
	private String sex;
	private String address;
	private String phone;
	private LocalDateTime loginDateTime;
	
	public MemberVO(String uid, String pwd, String name, String sex, String address, String phone, LocalDateTime loginDateTime) {
		super();
		this.uid = uid;
		this.pwd = pwd;
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.phone = phone;
		this.loginDateTime = loginDateTime;
	}
	
	public MemberVO() {
		System.out.println("MemberVO 생성자 호출");
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(uid, other.uid);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(uid);
	}

	

}
