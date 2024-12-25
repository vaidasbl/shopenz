import '../styles/global.scss';
import NavigationBar from '@/components/navigation-bar/NavigationBar';
import React from 'react';

const Home = () => {
  const navigationItems = [
    { name: 'Home', url: 'any' },
    { name: 'Menu', url: 'any' },
    { name: 'Contacts', url: 'any' },
    { name: 'Login', url: 'any' }
  ];

  return (
    <div>
      <NavigationBar NavigationItems={navigationItems} />
    </div>
  );
};
export default Home;
