//管理承运商数据
import axios from 'axios'

export async function getAllWarehouse() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/warehouse/findAllWarehouse')
    return result.data.data;
}

export async function addWarehouse(warehouse){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/warehouse/addWarehouse', warehouse);
    return result.data;
}

export async function editWarehouse(warehouse){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/warehouse/editWarehouse',warehouse)
    return result.data;
}

export async function deleteWarehouse(whId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/warehouse/deleteWarehouse/'+whId);
    return result.data;
}

export async function getWarehouseById(whId) {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/warehouse/deleteWarehouse',{
        params:{
            whId,
        }
    });
    return result.data;
}
