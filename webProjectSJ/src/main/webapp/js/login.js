/**
 * 
 */
window.onload = () => {

	let loginbtn = document.querySelector("#loginbtn");
	if (loginbtn != null) {
		loginbtn.onclick = (e) => {
			login(e);
		}
	}

	function login(e) {
		e.preventDefault();
		let param = {
			"user_id": user_id.value,
			"user_pwd": user_pwd.value
		};

		fetch('/webProjectSJ/Member/login.do', {
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
				location.href = jsonResult.url;
			});

	}

}
