// 회원정보변경
function update(userId) {
  let name = document.querySelector("#name").value.trim();
  let password = document.querySelector("#password").value.trim();
  let website = document.querySelector("#website").value.trim();
  let bio = document.querySelector("#bio").value.trim();
  let phone = document.querySelector("#phone").value.trim();
  let gender = document.querySelector("#gender").value.trim();
  
  let data = {
	  password: password,
	  name: name,
	  website: website,
	  bio: bio,
	  phone: phone,
	  gender: gender
  }
  
  $.ajax({
	  url: "http://localhost:8080/user",
	  method: "PUT",
	  data: JSON.stringify(data),
	  contentType: "application/json; charset=utf-8",
	  dataType: "json"
  }).done((res) => {
	  if (res.statusCode === 1) {
		  location.href = 'http://localhost:8080/user/'+userId;
	  }
  }).fail((err) => {
	  alert('에러가 발생했습니다.');
  })
};

// 구독자 정보 보기
document.querySelector("#subscribeBtn").onclick = (e) => {
  e.preventDefault();
  document.querySelector(".modal-follow").style.display = "flex";
  
  let id = window.location.pathname.split('/')[2];
  
  $.ajax({
  	type: "GET",
  	url: "http://localhost:8080/user/" + id + "/follow",
  	dataType: "json"
  })
  .done((res) => {
	if (res.statusCode === 1) {
	  	$("#follow_list").empty();
	  	res.data.forEach(dto => {
	  		let item = makeSubscribeInfo(dto);
	  		$("#follow_list").append(item);
	  	});
  	};
  })
  .fail((err) => {
  	console.log(err);
  	alert('불러오기 실패');
  	return;
  });
};

function makeSubscribeInfo(dto) {
	let item = `<div class="follower__item" id="follow-${dto.userId}">`;
	item += `<div class="follower__img">`;
	item += `<img src="/upload/${dto.profileImageUrl}" alt="" onerror="this.src='/images/person.jpg'">`;
	item += `</div>`;
	item += `<div class="follower__text">`;
	item += `<h2>${dto.username}</h2>`;
	item += `</div>`;
	item += `<div class="follower__btn">`;
	
	if (!dto.equalState) {
		if (dto.followState) {
			item += `<button onclick="clickModalFollow(${dto.userId})" class="cta">구독취소</button>`;				
		} else {
			item += `<button onclick="clickModalFollow(${dto.userId})" class="cta blue">구독하기</button>`;
		}
	}
	
	item += `</div>`;
	item += `</div>`;
	
	return item;
};

function clickModalFollow(toUserId) {
	if ($("#follow-"+toUserId+" button").text() == "구독취소") {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/follow/" + toUserId,
			dataType: 'json'
		}).done(res => {
			console.log(res);
			$("#follow-"+toUserId+" button").text("구독하기");
			$("#follow-"+toUserId+" button").attr('class', 'cta blue');
		}).fail(err => {
			console.log(err);
		});
	}
	
	if ($("#follow-"+toUserId+" button").text() == "구독하기") {
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/follow/" + toUserId
		}).done(res => {
			console.log(res);
			$("#follow-"+toUserId+" button").text("구독취소");
			$("#follow-"+toUserId+" button").attr('class', 'cta');
		}).fail(err => {
			console.log(err);
		});
	}
};

function clickFollow() {
	let toUserId = window.location.pathname.split('/')[2];
	let followCount = Number($("#span-follow-count").text());
	
	if ($("#profile-btn-follow").text() == "구독취소") {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8080/follow/" + toUserId,
			dataType: 'json'
		}).done(res => {
			console.log(res);
			$("#profile-btn-follow").text("구독하기");
			$("#profile-btn-follow").attr('class', 'cta blue');
			followCount = followCount - 1;
			$("#span-follow-count").text(followCount);
		}).fail(err => {
			console.log(err);
		});
	}
	
	if ($("#profile-btn-follow").text() == "구독하기") {
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/follow/" + toUserId
		}).done(res => {
			console.log(res);
			$("#profile-btn-follow").text("구독취소");
			$("#profile-btn-follow").attr('class', 'cta');
			followCount = followCount + 1;
			$("#span-follow-count").text(followCount);
		}).fail(err => {
			console.log(err);
		});
	}
};


function closeFollow() {
  document.querySelector(".modal-follow").style.display = "none";
}
document.querySelector(".modal-follow").addEventListener("click", (e) => {
  if (e.target.tagName !== "BUTTON") {
    document.querySelector(".modal-follow").style.display = "none";
  }
});
function popup(obj) {
  console.log(obj);
  document.querySelector(obj).style.display = "flex";
}
function closePopup(obj) {
  console.log(2);
  document.querySelector(obj).style.display = "none";
}
document.querySelector(".modal-info").addEventListener("click", (e) => {
  if (e.target.tagName === "DIV") {
    document.querySelector(".modal-info").style.display = "none";
  }
});
document.querySelector(".modal-image").addEventListener("click", (e) => {
  if (e.target.tagName === "DIV") {
    document.querySelector(".modal-image").style.display = "none";
  }
});
