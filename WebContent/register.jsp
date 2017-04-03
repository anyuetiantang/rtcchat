<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
   
<!-- 注册模块 -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">注册</h4>
            </div>
            <div class="modal-body">
            	<form role="form">
				  <div class="form-group">
            		<label for="usernameResgister">账号：</label>
            		<input type="text" class="form-control" id="usernameResgister" name="usernameResgister" placeholder="username" required autofocus />
				  </div>
				  <div class="form-group">
            		<label for="passwordRegister">密码：</label>
					<input type="password" class="form-control" id="passwordRegister" name="passwordRegister"  placeholder="password" required />				  </div>
				  <div class="form-group">
            		<label for="passwordConfirm">确认密码：</label>
					<input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm"  placeholder="password confirmed" required />				  </div>
				  <div class="form-group">
            		<label for="contactRegister">联系方式：</label>
            		<input type="text" class="form-control" id="contactRegister" name="contactRegister" placeholder="contact" required />
				  </div>
				</form>
            </div>
            <div class="modal-footer">
            	<button id="registerRequest" type="button" class="btn btn-primary">注册</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>