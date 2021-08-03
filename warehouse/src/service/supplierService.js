import axios from 'axios'

export async function getAllSupplier() {
    let result = await axios.get('http://localhost:8181/backEndWarehouse/supplier/findAllSupplier')
    return result.data.data;
}

export async function addSupplier(supplier){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/supplier/addSupplier',supplier);
    return result.data;
}

export async function editSupplier(supplier){
    let result = await axios.post('http://localhost:8181/backEndWarehouse/supplier/editSupplier',supplier)
    return result.data;
}

export async function deleteSupplier(splId) {
    let result = await axios.delete('http://localhost:8181/backEndWarehouse/supplier/deleteSupplier/'+splId);
    return result.data;
}