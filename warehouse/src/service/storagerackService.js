

import axios from 'axios'

export async function getAllStoragerack() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/storagerack/findAllStoragerack')
    return result.data.data;
}

export async function addStoragerack(storagerack){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/storagerack/addStoragerack', storagerack);
    return result.data;
}

export async function editStoragerack(storagerack){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/storagerack/editStoragerack',storagerack)
    return result.data;
}

export async function deleteStoragerack(srId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/storagerack/deleteStoragerack/'+srId);
    return result.data;
}

export async function getStoragerackBySrRaId(raId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/storagerack/getStoragerackBySrRaId',{
        params:{
            raId,
        }
    })
    return result.data;
}

export async function updateMaterial(mtId,mtName,srId){
    let result = await axios.get('http://localhost:8181/backEndWarehouse/storagerack/updateMaterial',{
        params:{
            mtId,
            mtName,
            srId,
        }
    })
    return result.data;
}

export async function clearMaterial(srId){
    let result = await axios.get('http://localhost:8181/backEndWarehouse/storagerack/clearMaterial',{
        params:{
            srId
        }
    })
    return result.data;
}
