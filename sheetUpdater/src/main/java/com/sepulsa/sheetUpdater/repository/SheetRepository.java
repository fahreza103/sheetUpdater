package com.sepulsa.sheetUpdater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sepulsa.sheetUpdater.entity.Sheet;

@Repository
public interface SheetRepository extends JpaRepository<Sheet, Long> {

	public Sheet findBySheetId(String sheetId);
}
