function menuClick(node) {
	var divNode = document.getElementById("aside");
	var childDivArr = divNode.getElementsByTagName("div");
	// var childArr = filterSpaceNode2(divNode.childNodes);
	// alert(childArr);

	var showDiv = node.nextSibling;
	for (var i = 0; i < childDivArr.length; i++) {
		if (childDivArr[i] === showDiv) {
		    if (childDivArr[i].className === "open") {
                childDivArr[i].className = "close";
            }else {
                childDivArr[i].className = "open";
            }

		} else {
			childDivArr[i].className = "close";
		}
	}
}

function aClick(node) {
	var path = node.title;
	// var path = node.getAttribute("title");
	var iframeNode = document.getElementById("myIframe");
	iframeNode.src = path;
}

function deleteLogin() {
	if(confirm("确定要注销并返回登录界面吗？")){
		window.location.href="JumpServlet?methodName=doBackLogin";
	}
}