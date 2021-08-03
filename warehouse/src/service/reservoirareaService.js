//管理承运商数据
import axios from 'axios'

export async function getAllReservoirarea() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/reservoirarea/findAllReservoirarea')
    return result.data.data;
}

export async function addReservoirarea(reservoirarea){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/reservoirarea/addReservoirarea', reservoirarea);
    return result.data;
}

export async function editReservoirarea(reservoirarea){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/reservoirarea/editReservoirarea',reservoirarea)
    return result.data;
}

export async function deleteReservoirarea(raId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/reservoirarea/deleteReservoirarea/'+raId);
    return result.data;
}


export async function getReservoirareaByWhId(whId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/reservoirarea/getReservoirareaByWhId',{
        params:{
            whId,
        }
    })
    return result.data;
}
