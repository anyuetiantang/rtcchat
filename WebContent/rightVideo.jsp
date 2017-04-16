<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 右侧视频区域 -->    
 <div id="rightVideo">
     <div class="drawer-contents">
        <div class="drawer-heading">
            <h2 class="drawer-title">Video</h2>
        </div>
        <div class="drawer-body list-group" style="padding: 0px;">
        	<a href="#" class="list-group-item">Cras justo odio</a>
			<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
			<a href="#" class="list-group-item">Morbi leo risus</a>
			<a href="#" class="list-group-item">Porta ac consectetur ac</a>
			<a href="#" class="list-group-item">Vestibulum at eros</a>
			<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
			<a href="#" class="list-group-item">Morbi leo risus</a>
			<a href="#" class="list-group-item">Porta ac consectetur ac</a>
			<a href="#" class="list-group-item">Vestibulum at eros</a>
			<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
			<a href="#" class="list-group-item">Morbi leo risus</a>
			<a href="#" class="list-group-item">Porta ac consectetur ac</a>
			<a href="#" class="list-group-item">Vestibulum at eros</a>
			<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
      	</div>
        <div class="drawer-footer locked text-center">
        	<small>&copy; Carlos</small>
        </div>
    </div>
</div>

<script>
	$('#rightVideo').BootSideMenu({
		side : "right",
		duration : 200,
		remember : true,
		autoClose : true,
		closeOnClick : true,
		width : "15%"
		});
</script>