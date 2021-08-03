//管理承运商数据
import axios from 'axios'

export async function getAllCarriers() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/carrier/findAllCarrier',{
        headers:{
            authorization: localStorage.getItem('Authorization')
        }
    })
    return result.data.data;
}

export async function addCarrier(carrier){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/carrier/addCarrier', carrier,{
        headers:{
            authorization: localStorage.getItem('Authorization')
        }
    });
    return result.data;
}

export async function editCarrier(carrier){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/carrier/editCarrier',carrier)
    return result.data;
}

export async function deleteCarrier(cId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/carrier/deleteCarrier',{
        params:{
            cId,
        }
    });
    return result.data;
}
