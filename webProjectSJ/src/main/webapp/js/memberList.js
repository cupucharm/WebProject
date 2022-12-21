/**
 * 
 */
window.onload = () => {

	let searchBtn = document.querySelector("#searchBtn");

	if (searchBtn != null) {
		searchBtn.onclick = () => {
			searchByUid();
		}
	}

	function searchByUid() {
		let searchInput = document.querySelector("#searchInput");

		window.location.href = '/webProjectSJ/Member/searchAdmin.do?searchInput=' + searchInput.value;
	}
	
	
	
	document.querySelectorAll(".deleteUids")
    	.forEach(aLink => {
    		aLink.addEventListener("click", e => {
    			e.preventDefault();
    	    	if (!confirm("삭제 하시겠습니까?")) return;
    	    	
    	    	let param = {
    	    		user_id : aLink.getAttribute("data-uid") 	
    	    	};
    	    	
    	    	fetch('/webProjectSJ/Member/memberAdminDelete.do', {
    	    		method : 'POST',
    	    		headers: {
    	    		    'Content-Type': 'application/json;charset=utf-8'
    	    		},
    	    		body: JSON.stringify(param)		
    	    	})
    	    	.then(response => response.json())
    	    	.then(jsonResult => {
    	    		alert(jsonResult.message);
    	    		if (jsonResult.status == true) {
    	    			location.reload();
    	    		}
    	    	});
    		
    	});
   	});
   	
   	
   	
   	document.querySelectorAll(".useYns")
	.forEach(aLink => {
		aLink.addEventListener("click", e => {
			e.preventDefault();
	    	let user_id = aLink.getAttribute("data-uid"); 
	    	let user_condition = aLink.getAttribute("data-ucondition"); 
			if (!confirm((user_condition == '활성화' ? '비활성화' : '활성화') +  "로 변경하시겠습니까?")) return;
	    	
	    	let param = {
	       		user_id : user_id 	
	       		,user_condition : user_condition 	
	    	};
	    	
	    	fetch('/webProjectSJ/Member/memberCondition.do', {
	    		method : 'POST',
	    		headers: {
	    		    'Content-Type': 'application/json;charset=utf-8'
	    		},
	    		body: JSON.stringify(param)		
	    	})
	    	.then(response => response.json())
	    	.then(jsonResult => {
	    		alert(jsonResult.message);
	    		if (jsonResult.status == true) {
	    			location.reload();
	    		}
	    	});
		});
	});
	
	

}

