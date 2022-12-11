package Board;

import lombok.Data;

@Data
public class BoardPageVO {
	public static final int amount = 10;
	
	private int startPage;
	private int endPage;
	private int currentPage;
	private int total;		// DB에 있는 게시글 수
	private boolean prev;
	private boolean next;
	
	public BoardPageVO(int currentPage, int total) {
		this.currentPage = currentPage;
		this.total = total;
		this.endPage = (int)Math.ceil(this.currentPage * 0.1) * amount;
		this.startPage = this.endPage - amount + 1;
		
		int realEnd = (int)Math.ceil(this.total / (double)amount);
		
		if(this.endPage > realEnd) {
			this.endPage = realEnd;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}

}
