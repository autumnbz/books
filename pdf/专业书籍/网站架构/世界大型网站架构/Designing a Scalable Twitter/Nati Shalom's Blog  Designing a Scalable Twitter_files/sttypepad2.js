var stTypePad2={jsonButtonString:"",widgetScript:"",loadScript:function(e,a){var d=document.getElementsByTagName("head")[0];var b=document.createElement("script");b.setAttribute("type","text/javascript");b.setAttribute("src",e);b.onload=a;b.onreadystatechange=function(){if(this.readyState=="complete"){a()}};d.appendChild(b)},loadWidget:function(a){stTypePad2.loadScript(a,stTypePad2.loadJQuery)},loadJQuery:function(){stTypePad2.loadScript("http://w.sharethis.com/widget/jquery-1.4.2.min.js",stTypePad2.createEntries)},createEntries:function(){jQuery(".entry").each(function(){var a="";var b=$(this).find(".entry-header a").attr("href");if(b===undefined){b=document.URL}var d=$(this).find(".entry-header").text();$.each(jsonButtonString,function(f,e){var h=(e[0].length)?("_"+e[0]):("");var l=' st_url="'+b+'"';var j=' st_title="'+d+'"';var g=(e[1].length>0)?(' displayText="'+e[1]+'"'):("");var i="";if(e[3]){i=" ";for(c=0;c<e[3].length;c++){i+=e[3][c][0]+'="'+e[3][c][1]+'" '}}a+='<span class="st_'+f+h+'"'+l+j+g+i+"></span>"});if($(this).find(".st-entry-footer-info").length){$(this).find(".entry-footer").find(".st-entry-footer-info").html(a)}else{$(this).find(".entry-footer").prepend("<p class='st-entry-footer-info'>"+a+"</p>")}});stTypePad2.loadScript(widgetScript,function(){(stTypePad2.publisherKey!=null)?stLight.options({publisher:stTypePad2.publisherKey}):stLight.options({publisher:"tp.00000000-0000-0000-0000-000000000000"})})},init:function(d,a,b){((typeof b)!="undefined")?(stTypePad2.publisherKey=b):(stTypePad2.publisherKey=null);jsonButtonString=a;widgetScript=d;stTypePad2.loadJQuery()}};