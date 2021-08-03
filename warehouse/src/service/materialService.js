
import axios from 'axios'

export async function getAllMaterial() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/material/findAllMaterial')
    return result.data.data;
}

export async function addMaterial(material){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/material/addMaterial', material);
    return result.data;
}

export async function editMaterial(material){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/material/editMaterial',material)
    return result.data;
}

export async function deleteMaterial(mtId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/material/deleteMaterial/'+mtId);
    return result.data;
}
