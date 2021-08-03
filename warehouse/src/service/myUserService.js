import axios from 'axios'

export async function getUserByCounter(id,password){
    let user = null
    await axios.get("http://localhost:8181/backEndWarehouse/user/findUserByIdAndPassword",{
        params:{
            uId:id,
            uPassword:password
        }
    }).then(res=>{
        user = res.data;
        console.log(user)
        if(res.data.code==200){

            let token = res.data.data.token;   //'Bearer ' +
            sessionStorage.setItem('Authorization', token);
        }
        // user = res.data;
        // let token = res.data.data.token;   //'Bearer ' +
        // // localStorage.setItem('Authorization', token);
        //  sessionStorage.setItem('Authorization', token);
    });
    return user;
}

export async function getAllUser() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/user/findAllUser')
    return result.data.data;
}
export async function findUserById(uId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/user/findUserById',{
        params:{
            uId
        }
    })
    return result.data.data;
}

export async function addUser(user){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/user/addUser',user);
    return result.data;
}

export async function editUser(user){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/user/editUser',user)
    return result.data;
}

export async function deleteUser(uId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/user/deleteUser/'+uId);
    return result.data;
}
export async function deleteImg(path){
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/user/deleteImg',{
        params:{
            path,
        }
    })
    return result.data
}
