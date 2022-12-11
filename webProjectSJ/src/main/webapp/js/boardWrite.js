/**
 * 
 */
window.onload = () => {
	let bwriter = document.querySelector('#bwriter');
	let uploadBtn = document.querySelector("#uploadBtn");
	let btitle = document.querySelector("#btitle");
	let bcategory = document.querySelector("#bcategory");

	ClassicEditor
		.create(document.querySelector('#bcontents'), { language: "ko" })
		.catch(error => {
			console.error(error);
		});


	if (uploadBtn != null) {
		uploadBtn.onclick = () => {
			checkNull();
		}
	}

	async function checkNull() {
		if (!(btitle != null && bcategory != null 
			&& bcontents != null )) {
			alert("내용을 확인하세요.");
		} else {
			uploadBoard();
		}
	}

	function uploadBoard() {
		let bcontents = document.querySelector(".ck-content");

		let param = {
			"btitle": btitle.value,
			"bcategory": bcategory.value,
			"bcontents": bcontents.innerHTML,
			"bwriter": bwriter.value
		};

		fetch('/webProjectSJ/Board/boardUpload', {
			//option
			method: 'POST',
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(param)
		})
			.then(response => response.json())
			.then(jsonResult => {
				alert(jsonResult.message);
				if (jsonResult.status == true) {
					location.href = jsonResult.url;
				}
			});
	}



}