/**
 * 
 */
window.onload = () => {

	ClassicEditor
		.create(document.querySelector('#bcontents'), { language: "ko" })
		.catch(error => {
			console.error(error);
		});
		
		
		let addFileListBtn = document.querySelector("#addFileListBtn");

	if (addFileListBtn != null) {
		addFileListBtn.onclick = () => {
			addFileList();
		}
	}

	async function addFileList() {
		file_list.innerHTML += "첨부파일 : <input type='file' name='filename1' id='filename1' multiple><br />";
	}

	
	
	let boardUpload = document.querySelector("#boardUpload");
	boardUpload.addEventListener("submit", (e) => {
		e.preventDefault();	//폼 수행을 정지시킨다.

		fetch('/webProjectSJ/Board/boardUpload', {
			
				method: 'POST',
          		cache: 'no-cache',
          		body: new FormData(boardUpload)})

			.then(response => response.json())
			.then(jsonResult => {
				alert(jsonResult.message);
				if (jsonResult.status == true) {
					location.href = jsonResult.url;
				}
			});
	});

}