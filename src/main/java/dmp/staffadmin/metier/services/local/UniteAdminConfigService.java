package dmp.staffadmin.metier.services.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dmp.staffadmin.dao.IUniteAdminConfigDao;
import dmp.staffadmin.dao.IUniteAdminDao;
import dmp.staffadmin.metier.entities.UniteAdmin;
import dmp.staffadmin.metier.interfaces.IUniteAdminConfigService;

@Service
@Transactional
public class UniteAdminConfigService implements IUniteAdminConfigService
{
	@Autowired
	private IUniteAdminConfigDao uniteAdminConfigDao;
	@Autowired
	private IUniteAdminDao uniteAdminDao;

	@Override
	public UniteAdmin getUniteAdminMere()
	{
		return uniteAdminDao.findById(uniteAdminConfigDao.findById(1L).get().getIdUniteAdminMere()).get();
	}

	@Override
	public UniteAdmin getCabinetUniteAdminMere()
	{
		return uniteAdminDao.findById(uniteAdminConfigDao.findById(1L).get().getIdCabinetUniteAdminMere()).get();
	}

	@Override
	public UniteAdmin getDRH()
	{
		return uniteAdminDao.findById(uniteAdminConfigDao.findById(1L).get().getIdDrh()).get();
	}

	@Bean
	CommandLineRunner testUAConfigService(IUniteAdminConfigService uaConfig)
	{
		return args ->
		{
			UniteAdmin DGMP = uaConfig.getUniteAdminMere();
			UniteAdmin cabDGMP = uaConfig.getCabinetUniteAdminMere();
			UniteAdmin DRH = uaConfig.getDRH();

			System.out.println("DGMP = " + DGMP);
			System.out.println("cabDGMP = " + cabDGMP);
			System.out.println("DRH = " + DRH);
		};
	}

}
