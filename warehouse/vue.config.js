//前端跨域配置文件

module.exports = {
    devServer:{
        proxy:{
            "/api":{ // 当请求地址以/api开头时,开发服务器代理到http://study.yuanjin.tech
                target:"http://localhost:8181/backEndWarehouse",
                ws: true,
                changeOrigin: true,
                pathRewrite:{
                    "^/api":"" //通过pathRewrite把前缀/api改为/，如果没有这个的话，则前端应用跨域地址时，如/api/Std/findAll
                                //就会被识别为http://localhost:8080/api/Std/findAll，这样就会报错，因为/api是不需要的，这个只是个识别符。
                }
            },
        }
    }
}