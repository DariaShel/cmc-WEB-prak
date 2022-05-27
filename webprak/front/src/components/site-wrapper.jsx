import {Link, Route, Routes} from "react-router-dom";
import React, {useEffect, useState} from "react";
import styled from "styled-components";
import Animals from "./animals";
import Workers from "./workers";
import {useLocation} from "react-router";
import Worker from "./worker";
import Animal from "./animal";
import Labels from "./labels";
import Label from "./label";
import Diseases from "./diseases";
import Disease from "./disease";

function SiteWrapper() {
    const loc = useLocation();
    const [pageName, setPageName] = useState('Животные');
    useEffect(()=>{
        if (loc.pathname == '/' || loc.pathname.includes('/animal/')) setPageName('Животные');
        else if (loc.pathname.includes('/worker')) setPageName('Работники');
        else if (loc.pathname.includes('/label')) setPageName('Метки');
        else if (loc.pathname.includes('/disease')) setPageName('Болезни');
    }, [loc]);
    return (
        <AppWrapper>
            <hr/>
            <PageName>{pageName}</PageName>
            <MenuWrapper>
                <MenuButton to={'/'}>Животные</MenuButton>
                <MenuButton to={'/workers/'}>Работники</MenuButton>
                <MenuButton to={'/labels/'}>Метки</MenuButton>
                <MenuButton to={'/diseases/'}>Болезни</MenuButton>
            </MenuWrapper>
            <Hr/>
            <Routes>
                <Route path='/' exact element={<Animals/>}/>
                <Route path='/workers/' element={<Workers/>}/>
                <Route path='/labels/' element={<Labels/>}/>
                <Route path='/diseases/' element={<Diseases/>}/>
                <Route path='/worker/:fId' element={<Worker/>}/>
                <Route path='/animal/:fId' element={<Animal/>}/>
                <Route path='/label/:fId' element={<Label/>}/>
                <Route path='/disease/:fId' element={<Disease/>}/>
            </Routes>
        </AppWrapper>
    );
}

const Hr = styled.hr`
  margin-bottom: 40px;
`;
const AppWrapper = styled.div`
  padding: 10px;
`;
const MenuWrapper = styled.div`
  display: flex;
  justify-content: space-around;
  margin-bottom: 10px;
`;
const MenuButton = styled(Link)`
  border-radius: 10px;
  background-color: lightblue;
  padding: 20px;
  text-decoration: none;
  font-family: "Bookman Old Style", serif;
  font-size: 20px;
  width: 110px;
  text-align: center;
`;
const PageName = styled.div`
  font-family: "Bookman Old Style", serif;
  text-align: center;
  font-size: 40px;
  margin-bottom: 10px;
`;

export default SiteWrapper;