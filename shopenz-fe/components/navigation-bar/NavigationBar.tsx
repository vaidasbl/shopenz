import React from 'react';
import './styles.scss';

type NavigationItem = {
  name: string;
  url: string;
}

type NavigationBarProps = {
  NavigationItems: NavigationItem[];
};

const NavigationBar = ({ NavigationItems }: NavigationBarProps) => {

  return (
    <div className="navigation-bar-container">
      {NavigationItems.map(item => {
        return <div key={item.name} className="navigation-item">{item.name}</div>;
      })}
    </div>
  );
};
export default NavigationBar;
