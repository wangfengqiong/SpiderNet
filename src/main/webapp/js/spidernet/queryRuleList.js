$(function(){	
	loadRuleList();	
	
});

$(document).ready(function() {
    $('#ruleForm').bootstrapValidator({
		message: 'This value is not valid',

        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	name: {
				validators: {
                    notEmpty: {
                        message: 'Please input Name.'
                   },
                    regexp: {
                        regexp: /^([\u4E00-\u9FA5]|\w)*$/,
                        message: 'Do not include special characters.'
                    },
                    stringLength: {
                        min: 1,
                        max: 50,
                        message: 'Please enter the name between 1 and 50 digits in length.'
                    },
                    remote: {
                        url: path+'/service/rule/checkNameExists',
                        message: 'Name already exists.',
                        delay :  2000,
                        type: 'POST'
                   },
                   
                  }
            },

            sort: {
                validators: {
                    notEmpty: {
                        message: 'Please input Sort.'
                    },
                    regexp: {
                        regexp: /^[1-9]\d*$/,
                        message: 'Please enter the number.'
                    },
                    stringLength: {
                        min: 1,
                        max: 11,
                        message: 'Please enter a length between 1 and 11 digits.'
                    },
                }
            }
        }
    }) .on('success.form.bv', function(e) {
    	// Prevent submit form
        e.preventDefault();

        var $form     = $(e.target);
            validator = $form.data('bootstrapValidator');
        if(validator){
        	saveRule(e.target);
        }                   
    }) ;
});

function loadRuleList(pageState){

	var ruleName = $("#ruleName").val();

	var pageState = pageState;
	
	$.ajax({
		url:path+"/service/rule/ruleInfoList",
		dataType:"json",
		async:true,
		data:{"ruleName":ruleName,"pageState":pageState},
		cache:false,
		type:"post",
		success:function(result){
			$("#ruleList tbody").remove();
			
			var tbody = $("<tbody>");
			tbody.appendTo($("#ruleList"));
			
			for (var i = 0; i < result.data.length; i++) {
				var tr = $("<tr></tr>");
				tr.appendTo(tbody);

				var td1 = $("<td>"
						+ result.data[i].name
						+ "</td>");
				var td2 = $("<td>"
						+ result.data[i].remark
						+ "</td>");
				var td3 = $("<td><a class='btn btn-info' href='javascript:void(0);'  onclick=edit('"+result.data[i].id+"')> <i class='glyphicon glyphicon-edit icon-white'></i> Edit</a>" +
						        "&nbsp<a class='btn btn-info' href='javascript:void(0);'  onclick=deleteRule('"+result.data[i].id+"')> <i class='glyphicon glyphicon-delete icon-white'></i> Delete</a></td>");
				td1.appendTo(tr);
				td2.appendTo(tr);
				td3.appendTo(tr);
			}
			$("#ruleList").append("</tbdoy>");
			var pageNum = parseInt(result.pageInfo.currentPage);
			pageNum = pageNum / 10 +1;
			var totalPage = parseInt(result.pageInfo.pageCount);
			$("#pageCount").html(totalPage);
			$("#currentPage").html(pageNum);
			$("#nextPage").attr("onclick","loadRuleList('next')");
			$("#previousPage").attr("onclick","loadRuleList('previous')");
			if(pageNum == totalPage){
				$("#nextPage").removeAttr("onclick");
			}
			if(pageNum == 1){
				$("#previousPage").removeAttr("onclick");
			}
		}
	})
}

function addRule(){	
	$('#myModal').modal('show');
}

function saveRule(){
	var id = $("#id").val();
	var name = $("#name").val();
	var sort = $("#sort").val();
	var remark = $("#remark").val();
	alert(id);
	if(id == null || id == ""){
		$.ajax({
			url:path+'/service/rule/addRule',
			dataType:"json",
			async:true,
			data:{"name":name,"sort":sort,"remark":remark},
			cache:false,
			type:"post",
			success:function(resultFlag){
				if(resultFlag){
					$("#successAlert").html('Add success!').show();
					setTimeout(function () {
					    $("#successAlert").hide();
					    $("#name").val("");
						$("#sort").val("");
						$("#remark").val("");
						$('#myModal').modal('hide');
				    }, 2000);
					loadRuleList();
				}else{
					$("#failureAlert").html('Add failure!').show();
				}
			}
		})		
	}
	else{
		$.ajax({
			url:path+'/service/rule/editRule',
			dataType:"json",
			async:true,
			data:{"id":id,"name":name,"sort":sort,"remark":remark},
			cache:false,
			type:"post",
			success:function(resultFlag){
				if(resultFlag){
					$("#successAlert").html('Edit success!').show();
					setTimeout(function () {
					    $("#successAlert").hide();
					    $("#id").val("");
					    $("#name").val("");
						$("#sort").val("");
						$("#remark").val("");
						$('#myModal').modal('hide');
				    }, 2000);
					loadRuleList();
				}else{
					$("#failureAlert").html('Edit failure!').show();
				}
			}
		})	
	}
		
	
		
}

function getRuleById(id){
$.ajax({
	url:path+'/service/rule/queryRuleById',
	dataType:"json",
	async:true,
	data:{"id":id},
	cache:false,
	type:"post",
	success:function(rule){
		$("#id").val(rule.id);
		$("#name").val(rule.name);
		$("#sort").val(rule.sort);
		$("#remark").val(rule.remark);
	}
})
}

function edit(id){
	getRuleById(id);
	$('#myModal').modal('show');	
}
function deleteRule(id){
		if(confirm("Confirm delete?")){
			$.ajax({
				url:path+'/service/rule/deleteRule',
				dataType:"json",
				async:true,
				data:{"id":id},
				cache:false,
				type:"post",
				success:function(resultFlag){
					if(resultFlag){
						alert("Delete success!");
						loadRuleList();
					}else{
						alert("Delete failure!");				
					}
				}
			})
			
		}
}






