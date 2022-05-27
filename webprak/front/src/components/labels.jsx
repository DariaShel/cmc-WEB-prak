import React, {useEffect, useState} from 'react';
import styled from "styled-components";
import axios from "axios";
import {Link} from "react-router-dom";

function Labels() {
    const [text, setText] = useState({
        'installation_time': '',
        'uninstallation_time': '',
        'id_animal': '',
        'id_employee': '',
        'id_label': ''
    });
    const [data, setData] = useState({});
    const textChange = (e) => setText(p => {
        const t = {...p};
        t[e.target.name] = e.target.value;
        return t;
    });
    const resp = async () => {
        const response = await axios.get('http://localhost:8080/api/labels/');
        for (let elem in response.data) {
            for (let p in response.data[elem]) {
                if (!(p in text)) {
                    delete response.data[elem][p];
                }
            }
        }
        setData(response.data);
    };
    useEffect(() => {
        resp();
    }, []);
    const filterBuild = () => {
        const rows = [];
        for (let elem in data) if (data[elem]['installation_time'].toString().includes(text.installation_time.toLowerCase()) && data[elem]['uninstallation_time'].toString().includes(text.uninstallation_time.toLowerCase()) && data[elem]['id_animal'].toString().includes(text.id_animal.toLowerCase()) && data[elem]['id_employee'].toString().includes(text.id_employee.toLowerCase()) && data[elem]['id_label'].toString().includes(text.id_label.toLowerCase())) {
            const rows1 = [];
            for (let p in data[elem]) {
                rows1.push(<RowElem key={p}>{data[elem][p]}</RowElem>);
            }
            if (rows.length == 0) {
                const rows2 = [];
                for (let p in data[elem]) {
                    rows2.push(<RowElem key={p}>{p}</RowElem>);
                }
                rows.push(
                    <RowHeader key={'header'}>{rows2}</RowHeader>);
            }
            rows.push(
                <Row key={data[elem]['id_label']} id={data[elem]['id_label']}
                     to={'/label/' + data[elem]['id_label'] + '/'}>{rows1}</Row>);
        }
        if (rows.length > 1) return rows;
        else return <div id={'no_labels'}>Таким фильтрам не удовлетворяет ни одна метка</div>;
    };
    const add = async () => {
        await axios.post('http://localhost:8080/api/labels/', {
            'id_label': '10005',
            'id_animal': '1',
            'installation_time': '1-1-1',
            'uninstallation_time': '1-1-1',
            'id_employee': '2'
        });
        setTimeout(() => {
            resp();
        }, 500);
    }
    return (<div>
        <SearchWrapper>
            <div>
                <FilterWrapper>
                    <FilterPoint>{'Фильтры:'}<br/></FilterPoint>
                    <FilterPoint>
                        id_label:
                        <InputWrapper type="text" name="id_label" value={text['id_label']}
                                      onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Animal ID:
                        <InputWrapper type="text" name="id_animal" value={text['id_animal']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Installation time:
                        <InputWrapper type="text" name="installation_time" value={text['installation_time']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Uninstallation time:
                        <InputWrapper type="text" name="uninstallation_time" value={text['uninstallation_time']} onChange={textChange}/><br/>
                    </FilterPoint>
                    <FilterPoint>
                        Employee ID:
                        <InputWrapper type="text" name="id_employee" value={text['id_employee']}
                                      onChange={textChange}/><br/>
                    </FilterPoint>
                </FilterWrapper>
                <AddButton id={'add'} onClick={add}>Добавить метку</AddButton>
            </div>
            <OutputWrapper>{filterBuild()}</OutputWrapper>
        </SearchWrapper>
    </div>);
}

const InputWrapper = styled.input`
  margin-left: 5px;
  border-radius: 10px;
  padding: 5px;
  border: none;
`;
const FilterPoint = styled.div`
  margin-bottom: 10px;
`;
const FilterWrapper = styled.div`
  margin-right: 30px;
  border-radius: 10px;
  background-color: ghostwhite;
  padding: 10px;
  margin-bottom: 40px;
  text-align: center;
`;
const OutputWrapper = styled.div`
  width: 75%;
  padding: 0 10px;
`;
const SearchWrapper = styled.div`
  display: flex;
`;
const AddButton = styled.div`
  border-radius: 10px;
  padding: 10px;
  background-color: lightblue;
  cursor: pointer;
  display: inline;
`;
const Row = styled(Link)`
  border-radius: 20px;
  background-color: lightblue;
  display: flex;
  padding: 10px;
  justify-content: space-around;
  text-decoration: none;
  margin-bottom: 5px;
`;
const RowHeader = styled.div`
  border-radius: 20px;
  background-color: pink;
  display: flex;
  padding: 10px;
  justify-content: space-around;
  text-decoration: none;
  margin-bottom: 5px;
`;
const RowElem = styled.div`
  width: 200px;
  text-align: center;
  color: black;
`;

export default Labels;