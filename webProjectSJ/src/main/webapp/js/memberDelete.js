/**
 * 
 */
window.onload = () => {
	let before_update_pwdBtn = document.querySelector("#before_update_pwdBtn");

	if (before_update_pwdBtn != null) {
		before_update_pwdBtn.onclick = () => {
			pwdUpdateCheck();
		}
	}

	async function pwdUpdateCheck() {

		let response = await fetch('/webProjectSJ/Member/pwdUpdateCheck?user_pwd=' + before_pwd.value);
		let jsonResult = await response.json();

		pwd_msg.innerHTML = jsonResult.message;
	}



	let deleteMemberBtn = document.querySelector("#deleteMemberBtn");
	if (deleteMemberBtn != null) {
		deleteMemberBtn.onclick = () => {
			deleteMember();

		};
	}

	function deleteMember() {

		document.getElementById("pwd_msg").style.display = "none";

		fetch('/webProjectSJ/Member/pwdUpdateCheck?user_pwd=' + before_pwd.value )
			.then(response => response.json())
			.then(jsonResult => {
				if (jsonResult.status == false) {
					alert(jsonResult.message);
				} else {
					fetch('/webProjectSJ/Member/memberDelete')
						.then(response => response.json())
						.then(jsonResult => {
							if (jsonResult.status == false) {
								alert(jsonResult.message);
							} else {
								alert(jsonResult.message);
								window.location.href = '/webProjectSJ/page/MainPage.jsp';
							}
						})
				location.reload();
				}
			})
	}

}