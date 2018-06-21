definition(
    name: "Báo cháy ",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
//Test:OK
preferences {
  section("Báo cháy"){
   input "smokeH", "capability.smokeDetector", title: "Chọn cảm biến cháy"
  }
  
section("Báo động"){
   input("alamH","capability.alarm",title:"Chọn loa báo động")
   }
}
 section("Số điện thoại nhận tin nhắn")
    	{
        	input name:"txtmobi",type:"text", title:"Số điện thoại:+84SĐT",defaultValue:" "
         }
def init()
{
	subscribe(smokeH, "smoke", smoke_H)
  	subscribe(alamH, "alarm", alam_H)
}

def installed() 
{
	init()
}

def updated() 
{
	unschedule()
   	init()	
}

def smoke_H(evt) {
  if("detected" == evt.value) 
  {
  	alamH.siren()
  	sendPush("Phát hiện cháy tại: ${evt.displayName}")
    sendSms({$txtmobi},"Phát hiện cháy tại: ${evt.displayName}. Hãy kiểm tra lại")
  }
   if("tested" == evt.value) 
  {
  	alamH.siren()
  	sendPush("[Đang sử dụng nút Test]: Phát hiện cháy tại: ${evt.displayName}")
    sendSms({$txtmobi},"[Đang sử dụng nút Test]: Phát hiện cháy tại: ${evt.displayName}. Hãy kiểm tra lại")
  }
}